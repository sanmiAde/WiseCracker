package com.adetech.wisecracker.ui.savedJokeList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.adetech.wisecracker.R;
import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

public class SavedJokeListActivity extends AppCompatActivity implements  SavedJokeListAdapter.JokeListAdapterOnItemClickHandler, SavedJokeListAdapter.ShowJokeSaveItemCLickHandler
{

    private SavedJokeListViewModel mSavedJokeListViewModel;

    private RecyclerView mRecyclerView;

    private SavedJokeListAdapter mAdapter;

    private TextView mEmptyTextView;

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SavedJokeListActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_jokes_list);

        mAdapter = setupRecyclerView();
        mEmptyTextView = findViewById(R.id.empty);

       mSavedJokeListViewModel = ViewModelProviders.of(this).get(SavedJokeListViewModel.class);

        mSavedJokeListViewModel.getJokeList().observe(this, new Observer<List<Joke>>()
        {
            @Override
            public void onChanged(@Nullable List<Joke> jokes)
            {

                if (jokes != null)
                {
                    if (jokes.isEmpty())
                    {
                        mEmptyTextView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                    } else
                    {
                        mAdapter.setJokes(jokes);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mEmptyTextView.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });
    }

    private SavedJokeListAdapter setupRecyclerView()
    {
        mRecyclerView = findViewById(R.id.recyclerview_jokeList);
        final SavedJokeListAdapter adapter = new  SavedJokeListAdapter(this, this, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        return adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.saved_joke, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
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
    public void onItemLongClicked(final int jokeId)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        alertDialogBuilder.setTitle("Delete joke");

        alertDialogBuilder
                .setMessage("You know the drill.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        mSavedJokeListViewModel.deleteJoke(jokeId);

                        Toast.makeText(SavedJokeListActivity.this, "Joke deleted", Toast.LENGTH_SHORT).show();
                        mAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
    }

