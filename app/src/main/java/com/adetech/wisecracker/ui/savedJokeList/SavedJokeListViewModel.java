package com.adetech.wisecracker.ui.savedJokeList;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.adetech.wisecracker.data.JokeRepository;
import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

public class SavedJokeListViewModel extends AndroidViewModel
{
    private final JokeRepository mRepository;
    private final LiveData<List<Joke>> mJokeList;

    public SavedJokeListViewModel(Application application)
    {
        super(application);
        mRepository = new JokeRepository(application);
        mJokeList = mRepository.getAllJokesFromDb();
    }

    LiveData<List<Joke>> getJokeList()
    {
        return  mJokeList;
    }

    void deleteJoke(int jokeId)
    {
        mRepository.deleteJokeById(jokeId);
    }

}
