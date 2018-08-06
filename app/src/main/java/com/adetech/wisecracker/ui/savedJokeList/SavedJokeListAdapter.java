package com.adetech.wisecracker.ui.savedJokeList;

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

public class SavedJokeListAdapter extends RecyclerView.Adapter<SavedJokeListAdapter.SavedJokeViewHolder>
{

    private final Context mContext;

    private List<Joke> mJokes;

    private final SavedJokeListAdapter.JokeListAdapterOnItemClickHandler mClickHandler;

    private final SavedJokeListAdapter.ShowJokeSaveItemCLickHandler mLongClickHandler;


    public interface JokeListAdapterOnItemClickHandler
    {
        void onItemClick(String joke);
    }

    public interface ShowJokeSaveItemCLickHandler
    {
        void onItemLongClicked(int jokeId);
    }


    public SavedJokeListAdapter(Context context, JokeListAdapterOnItemClickHandler clickHandler, ShowJokeSaveItemCLickHandler longClickHandler)
    {
        mContext = context;
        this.mClickHandler = clickHandler;
        mLongClickHandler = longClickHandler;

    }


    @NonNull
    @Override
    public SavedJokeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_saved_jokes_item, parent, false);

        return new SavedJokeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedJokeViewHolder holder, int position)
    {
        Joke joke = mJokes.get(position);
        holder.jokeTextView.setText(joke.getJoke());
    }

    @Override
    public int getItemCount()
    {
        return mJokes != null ? mJokes.size() : 0;
    }

    void setJokes(List<Joke> words)
    {
        mJokes = words;
        notifyDataSetChanged();
    }



    public class SavedJokeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        private TextView jokeTextView;


        public SavedJokeViewHolder(View itemView)
        {
            super(itemView);
            jokeTextView = itemView.findViewById(R.id.joke);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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
            int jokeId = mJokes.get(adapterPostion).getId();

            mLongClickHandler.onItemLongClicked(jokeId);
            return  true;
    }
    }
}
