package test.freelancer.com.fltest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.freelancer.com.fltest.R;
import test.freelancer.com.fltest.dao.Program;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVHolder> {
    
    List<Program> mPrograms;
    
    public TVAdapter(List<Program> programs) {
        this.mPrograms = programs;
    }

    public TVAdapter() {
        this.mPrograms = new ArrayList<>();
    }

    @Override
    public TVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_tv_programs, parent, false);
        TVHolder vh = new TVHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TVHolder holder, int position) {
        holder.mListItemTvProgramsTitle.setText(mPrograms.get(position).getName());
        holder.mListItemTvProgramsStart.setText(mPrograms.get(position).getStart_time());
        holder.mListItemTvProgramsEnd.setText(mPrograms.get(position).getEnd_time());
        holder.mListItemTvProgramsChannel.setText(mPrograms.get(position).getChannel());
        holder.mListItemTvProgramsRating.setText(mPrograms.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return mPrograms.size();
    }
    
    public void add(Program program) {
        mPrograms.add(program);
        notifyDataSetChanged();
    }

    public void add(List<Program> programs) {
        mPrograms.addAll(programs);
        notifyDataSetChanged();
    }
    
    static class TVHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.list_item_tv_programs_title) TextView mListItemTvProgramsTitle;
        @Bind(R.id.list_item_tv_programs_start) TextView mListItemTvProgramsStart;
        @Bind(R.id.list_item_tv_programs_end) TextView mListItemTvProgramsEnd;
        @Bind(R.id.list_item_tv_programs_channel) TextView mListItemTvProgramsChannel;
        @Bind(R.id.list_item_tv_programs_rating) TextView mListItemTvProgramsRating;

        TVHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
