package com.adetech.wisecracker.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities =  {Joke.class}, version =   1)
public abstract class JokeRoomDatabase extends RoomDatabase
{
    public abstract JokeDao mJokeDao();

    private static JokeRoomDatabase INSTANCE;

    public static JokeRoomDatabase getINSTANCE(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (JokeRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JokeRoomDatabase.class, "joke_database").build();
                }
            }
        }

        return INSTANCE;
    }
}
