package com.adetech.wisecracker.ui.savedJokeList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class SavedJokeListActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SavedJokeListActivity.class);

        return intent;
    }
}
