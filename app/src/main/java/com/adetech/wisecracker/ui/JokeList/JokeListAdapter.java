package com.adetech.wisecracker.ui.JokeList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adetech.wisecracker.R;
import com.adetech.wisecracker.data.database.Joke;

import java.util.List;

public class JokeListAdapter extends RecyclerView.Adapter<JokeListAdapter.JokeListViewHolder>
{

    private final JokeListAdapterOnItemClickHandler mClickHandler;

    private final ShowJokeSaveItemCLickHandler mLongClickHandler;

    private final Context mContext;

    private List<Joke>  mJokes;



    JokeListAdapter(@NonNull Context context, JokeListAdapterOnItemClickHandler clickHandler, ShowJokeSaveItemCLickHandler longClickHandler)
    {

        mContext = context;
        this.mClickHandler = clickHandler;
        mLongClickHandler = longClickHandler;
    }

    @NonNull
    @Override
    public JokeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_joke_item,parent, false
        );
        return new JokeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeListViewHolder holder, int position)
    {
        Joke joke = mJokes.get(position);
        holder.jokeTextView.setText(joke.getJoke());

    }

    @Override
    public int getItemCount()
    {
        return mJokes != null ? mJokes.size() : 0;
    }

    void  setJokes(List<Joke> words)
    {
        mJokes = words;
        notifyDataSetChanged();
    }


    public interface JokeListAdapterOnItemClickHandler {
        void onItemClick( String joke);
    }

    public interface ShowJokeSaveItemCLickHandler
    {
       void onItemLongClicked(String joke);
    }


    public class JokeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private TextView jokeTextView;

        public JokeListViewHolder(View view)
        {
            super(view);
            jokeTextView = view.findViewById(R.id.joke);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            int adapterPostion = getAdapterPosition();
            String joke = mJokes.get(adapterPostion).getJoke();

            mClickHandler.onItemClick(joke);
        }


        @Override
        public boolean onLongClick(View view)
        {
            int adapterPostion = getAdapterPosition();
            String joke = mJokes.get(adapterPostion).getJoke();

            mLongClickHandler.onItemLongClicked(joke);
            return  true;
        }
    }
}
