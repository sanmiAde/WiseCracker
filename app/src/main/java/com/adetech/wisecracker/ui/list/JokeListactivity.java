package com.adetech.wisecracker.ui.list;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adetech.wisecracker.R;
import com.adetech.wisecracker.data.database.Joke;

import java.util.Collections;
import java.util.List;


public class JokeListactivity extends AppCompatActivity implements JokeListAdapter.JokeListAdapterOnItemClickHandler , JokeListAdapter.ShowJokeSaveItemCLickHandler
{
    private JokeListViewModel mMainActivityViewModel;

    private RecyclerView mRecyclerView;

    private ProgressBar mLoadingIndicator;

    private Boolean isSaveVisible = false;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_jokes_acitivity);

        final JokeListAdapter  adapter = setupRecyclerView();

        mMainActivityViewModel = ViewModelProviders.of(this).get(JokeListViewModel.class);

        mMainActivityViewModel.getJokeList().observe(this, new Observer<List<Joke>>()
        {
            @Override
            public void onChanged(@Nullable List<Joke> jokes)
            {
                 Collections.shuffle(jokes);
                 adapter.setJokes(jokes);
            }
        });
    }


    private JokeListAdapter setupRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerview_jokeList);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

       final JokeListAdapter adapter = new JokeListAdapter(this,this, this);
       mRecyclerView.setAdapter(adapter);
       mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


       return adapter;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
      getMenuInflater().inflate(R.menu.joke_list, menu);

      return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
       int id = item.getItemId();

       if(id == R.id.save)
       {
           return true;
       }

        return super.onOptionsItemSelected(item);
    }


    private void showJokeDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the weather data is visible
        mRecyclerView.setVisibility(View.VISIBLE);
    }



    private void showLoading() {
        // Then, hide the weather data
        mRecyclerView.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(String joke)
    {
        Intent sendIntent = new Intent();

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, joke);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);

    }


    @Override
    public void  onItemLongClicked(final String joke)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Save joke for friend.");

        alertDialogBuilder
                .setMessage("You know the drill.")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        mMainActivityViewModel.saveJoke(joke);
                        
                        Toast.makeText(JokeListactivity.this, "Joke saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    }
