package com.adetech.wisecracker.ui.list;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

    private final Context mContext;

    private List<Joke>  mJokes;



    JokeListAdapter(@NonNull Context context, JokeListAdapterOnItemClickHandler clickHandler)
    {

        mContext = context;
        this.mClickHandler = clickHandler;
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


    public class JokeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView jokeTextView;

        public JokeListViewHolder(View view)
        {
            super(view);
            jokeTextView = view.findViewById(R.id.joke);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            int adapterPostion = getAdapterPosition();
            String joke = mJokes.get(adapterPostion).getJoke();

            mClickHandler.onItemClick(joke);
        }


//        private ItemTouchHelper.Callback createHelpetCallback()
//        {
//            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
//            {
//                @Override
//                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
//                {
//                    return false;
//                }
//
//                @Override
//                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
//                {
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//
//                    View itemView = viewHolder.itemView;
//                    int itemHeight = itemView.getBottom()  - itemView.getTop();
//
//
//                }
//
//                @Override
//                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
//                {
//                    String joke = mJokes.get(viewHolder.getAdapterPosition()).getJoke();
//
//
//                }
//            }
//        }


    }
}
