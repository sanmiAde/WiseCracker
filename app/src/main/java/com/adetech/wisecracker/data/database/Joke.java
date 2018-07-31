package com.adetech.wisecracker.data.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "joke_table")
public class Joke
{
    public void setId(@NonNull int id)
    {
        mId = id;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int mId;

    @NonNull
    @ColumnInfo(name = "joke")
    @SerializedName("Joke")
    private String mJoke;


    public Joke(@NonNull String joke)
    {
        this.mJoke = joke;

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
