package com.adetech.wisecracker.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adetech.wisecracker.R;
import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

public class JokeListactivity extends AppCompatActivity
{
    private JokeListViewModel mMainActivityViewModel;
    private TextView mTextView;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_jokes_acitivity);

        mMainActivityViewModel = ViewModelProviders.of(this).get(JokeListViewModel.class);
        mTextView = findViewById(R.id.test);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        mMainActivityViewModel.getJokeList().observe(this, new Observer<List<Joke>>()
        {

            @Override
            public void onChanged(@Nullable List<Joke> jokes)
            {

                if(jokes != null && jokes.size() != 0)
                {
                    for (Joke joes : jokes)
                    {
                        showJokeDataView();
                        mTextView.append(joes.getJoke());
                        mTextView.append("\n\n\n\n");
                    }
                }
                else
                {
                   showLoading();
                }


            }
        });

    }
    private void showJokeDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the weather data is visible
        mTextView.setVisibility(View.VISIBLE);
    }

    private void showLoading() {
        // Then, hide the weather data
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

}
