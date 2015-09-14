package test.freelancer.com.fltest.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.freelancer.com.fltest.R;
import test.freelancer.com.fltest.dao.Program;

public class TVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_PROGRAMS = 1;

    List<Program> mPrograms;
    boolean loadMore = true;
    
    public HashMap<String, Integer> contentColor;

    public TVAdapter(List<Program> programs) {
        this.mPrograms = programs;
        contentColor = new HashMap<>();
    }

    public TVAdapter() {
        this.mPrograms = new ArrayList<>();
        contentColor = new HashMap<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_loading, parent, false);
            ProgressViewHolder viewHolder = new ProgressViewHolder(v);
            return viewHolder;
        }
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tv_programs, parent, false);
        TVHolder vh = new TVHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_LOADING) {
            return;
        }
        TVHolder holder = (TVHolder) viewHolder;
        holder.mListItemTvProgramsTitle.setText(mPrograms.get(position).getName());
        holder.mListItemTvProgramsTime.setText(mPrograms.get(position).getStart_time() + " - " +
                mPrograms.get(position).getEnd_time());
        holder.mListItemTvProgramsChannel.setText(mPrograms.get(position).getChannel());
        holder.mListItemTvProgramsRating.setText(mPrograms.get(position).getRating());
        
        if (contentColor.containsKey(mPrograms.get(position).getRating())) {
            int color = contentColor.get(mPrograms.get(position).getRating());
            holder.mListItemParent.setBackgroundColor(color);
        } else {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            contentColor.put(mPrograms.get(position).getRating(), color);
            holder.mListItemParent.setBackgroundColor(color);
        }
    }

    @Override
    public int getItemCount() {
        return loadMore ? mPrograms.size() + 1 : mPrograms.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mPrograms.size() ? VIEW_TYPE_LOADING : VIEW_TYPE_PROGRAMS;
    }

    public void add(Program program) {
        mPrograms.add(program);
        notifyDataSetChanged();
    }

    public void add(List<Program> programs) {
        mPrograms.addAll(programs);
        notifyDataSetChanged();
    }

    public void disableLoading() {
        loadMore = false;
        notifyDataSetChanged();
    }

    static class TVHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.card_view) CardView mListItemParent;
        @Bind(R.id.list_item_tv_programs_title) TextView mListItemTvProgramsTitle;
        @Bind(R.id.list_item_tv_programs_time) TextView mListItemTvProgramsTime;
        @Bind(R.id.list_item_tv_programs_channel) TextView mListItemTvProgramsChannel;
        @Bind(R.id.list_item_tv_programs_rating) TextView mListItemTvProgramsRating;

        TVHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    
    static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progress) ProgressBar mProgress;

        ProgressViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
