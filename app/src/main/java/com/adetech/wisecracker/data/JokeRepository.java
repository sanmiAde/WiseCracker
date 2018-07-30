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

//TODO optimise read operation

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
