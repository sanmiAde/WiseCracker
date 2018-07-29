package com.adetech.wisecracker.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "joke_table")
public class Joke
{
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName( "Id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "joke")
    @SerializedName("Joke")
    private String mJoke;


    public Joke(@NonNull String joke, int id)
    {
        this.mJoke = joke;
        this.mId = id;
    }

    @NonNull
    public int getId()
    {
        return mId;
    }

    @NonNull
    public String getJoke()
    {
        return mJoke;
    }
}
