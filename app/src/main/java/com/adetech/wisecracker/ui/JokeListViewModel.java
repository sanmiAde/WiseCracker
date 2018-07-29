package com.adetech.wisecracker.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.adetech.wisecracker.data.JokeRepository;
import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

public class JokeListViewModel extends AndroidViewModel
{
    private final JokeRepository mRepository;
    private final LiveData<List<Joke>> mJokeList;

    public JokeListViewModel(Application application)
    {
        super(application);
        mRepository = new JokeRepository(application);
        mJokeList = mRepository.getAllJokes();
    }

    LiveData<List<Joke>> getJokeList()
    {
        return  mJokeList;
    }
}
