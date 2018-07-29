package com.adetech.wisecracker.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface JokeDao
{
    @Insert
    void insert(Joke joke);

    @Query("SELECT * FROM joke_table ORDER BY id")
    LiveData<List<Joke>> getAllJokes();

    @Query("DELETE FROM joke_table")
    void deleteAll();

    @Delete
    void delete(Joke joke);
}
