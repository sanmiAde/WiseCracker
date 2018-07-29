package com.adetech.wisecracker.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.adetech.wisecracker.data.database.Joke;
import com.adetech.wisecracker.data.database.JokeDao;
import com.adetech.wisecracker.data.database.JokeRoomDatabase;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class JokeRepository
{
    private LiveData<List<Joke>> mAllJokes;
    private JokeDao mJokeDao;
    private Application mApplication;

//    private  String json = "[\n" +
//        "  {\n" +
//        "    \"ID\": 1,\n" +
//        "    \"Joke\": \"What did the bartender say to the jumper cables? You better not try to start anything.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 2,\n" +
//        "    \"Joke\": \"Don't you hate jokes about German sausage? They're the wurst!\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 3,\n" +
//        "    \"Joke\": \"Two artists had an art contest... It ended in a draw\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 4,\n" +
//        "    \"Joke\": \"Why did the chicken cross the playground? To get to the other slide.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 5,\n" +
//        "    \"Joke\": \"What gun do you use to hunt a moose? A moosecut!\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 6,\n" +
//        "    \"Joke\": \"If life gives you melons, you might have dyslexia.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 7,\n" +
//        "    \"Joke\": \"Broken pencils... ...are pointless.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 8,\n" +
//        "    \"Joke\": \"What did one snowman say to the other snowman? 'Do you smell carrots?'\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 9,\n" +
//        "    \"Joke\": \"How many hipsters does it take to change a lightbulb? It's a really obscure number. You've probably never heard of it.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 10,\n" +
//        "    \"Joke\": \"Where do sick boats go? The dock!\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 11,\n" +
//        "    \"Joke\": \"I like my slaves like I like my coffee: Free.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 12,\n" +
//        "    \"Joke\": \"My girlfriend told me she was leaving me because I keep pretending to be a Transformer... I said, No, wait! I can change!\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 13,\n" +
//        "    \"Joke\": \"Old Chinese proverb: Man who not shower in 7 days makes one reek.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 14,\n" +
//        "    \"Joke\": \"What did the owner of a brownie factory say when his factory caught fire? \\\"I'm getting the fudge outta here!\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 15,\n" +
//        "    \"Joke\": \"What form of radiation bakes you cookies? A gramma ray\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 16,\n" +
//        "    \"Joke\": \"Bee jokes, courtesy of my niece (age 8). What did the bee use to dry off after swimming? A *bee*ch towel. What did the bee use to get out the tangles? A honeycomb.\"\n" +
//        "  },\n" +
//        "  {\n" +
//        "    \"ID\": 17,\n" +
//        "    \"Joke\": \"What's the loudest economic system? CAPITALISM\"\n" +
//        "  }]";

    public JokeRepository(Application application)
    {
        JokeRoomDatabase db = JokeRoomDatabase.getINSTANCE(application);
        mJokeDao = db.mJokeDao();
        mApplication = application;
        mAllJokes = mJokeDao.getAllJokes();
    }

    LiveData<List<Joke>> getAllJokesFromDb()
    {
        return mAllJokes;
    }

    public void insertJoke(Joke joke)
    {
        new insertAsyncTask(mJokeDao).execute(joke);
    }

    public void deleteJoke(Joke joke)
    {
        new deleteAsyncTask(mJokeDao).execute(joke);
    }

    public void deleteAllJokes()
    {
        new deleteAllAsyncTask(mJokeDao).execute();
    }

    public LiveData<List<Joke>> getAllJokes()
    {

        return new convertJsonToJokes().doInBackground(getInputStreamFromAsset());
    }


    private InputStream getInputStreamFromAsset()
    {
        InputStream is;

        try
        {
            is = mApplication.getAssets().open("clean_joke.json");
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }

        return is;
    }

    private static class insertAsyncTask extends AsyncTask<Joke, Void, Void>
    {
        private JokeDao mJokeDao;


        public insertAsyncTask(JokeDao jokeDao)
        {
            mJokeDao = jokeDao;
        }


        @Override
        protected Void doInBackground(Joke... jokes)
        {
            mJokeDao.insert(jokes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Joke, Void, Void>
    {
        private JokeDao mJokeDao;

        public deleteAsyncTask(JokeDao jokeDao)
        {
            mJokeDao = jokeDao;
        }


        @Override
        protected Void doInBackground(Joke... jokes)
        {
            mJokeDao.delete(jokes[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Joke, Void, Void>
    {
        private JokeDao mJokeDao;

        public deleteAllAsyncTask(JokeDao jokeDao)
        {
            mJokeDao = jokeDao;
        }


        @Override
        protected Void doInBackground(Joke... jokes)
        {
            mJokeDao.deleteAll();
            return null;
        }

    }

    private static class convertJsonToJokes extends AsyncTask<InputStream, Void, LiveData<List<Joke>>>
    {
        Gson mGson = new Gson();

//TODO optimise json file reading

        public String loadJSONFromAsset(InputStream is)
        {
            String json = null;
            try
            {
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            } catch (IOException ex)
            {
                ex.printStackTrace();
                return null;
            }
            return json;
        }

        @Override
        protected LiveData<List<Joke>> doInBackground(InputStream... is)
        {

            List<Joke> jokes = Arrays.asList(mGson.fromJson(loadJSONFromAsset(is[0]), Joke[].class));

            MutableLiveData<List<Joke>> liveDataJoke = new MutableLiveData<>();

            liveDataJoke.setValue(jokes);

            return liveDataJoke;
        }
    }
}
