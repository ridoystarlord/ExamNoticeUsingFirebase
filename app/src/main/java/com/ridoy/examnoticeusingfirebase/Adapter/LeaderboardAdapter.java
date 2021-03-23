package com.ridoy.examnoticeusingfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ridoy.examnoticeusingfirebase.ModelClass.LeaderboardModel;
import com.ridoy.examnoticeusingfirebase.R;
import com.ridoy.examnoticeusingfirebase.databinding.LeaderboardLayoutBinding;

import java.util.ArrayList;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.LeadeboardViewHolder> {

    Context context;
    ArrayList<LeaderboardModel> leaderboardlist;

    public LeaderboardAdapter(Context context, ArrayList<LeaderboardModel> leaderboardlist) {
        this.context = context;
        this.leaderboardlist = leaderboardlist;
    }

    @NonNull
    @Override
    public LeadeboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.leaderboard_layout, parent, false);
        return new LeadeboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeadeboardViewHolder holder, int position) {
        LeaderboardModel leaderboardModel = leaderboardlist.get(position);
        holder.leaderboardLayoutBinding.nameTV.setText(leaderboardModel.getName());
        holder.leaderboardLayoutBinding.positionTV.setText(String.valueOf(leaderboardlist.size()-position));
        holder.leaderboardLayoutBinding.pointTV.setText(String.valueOf(leaderboardModel.getCurrentpoint()));
    }

    @Override
    public int getItemCount() {
        return leaderboardlist.size();
    }

    public class LeadeboardViewHolder extends RecyclerView.ViewHolder {
        LeaderboardLayoutBinding leaderboardLayoutBinding;

        public LeadeboardViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderboardLayoutBinding = LeaderboardLayoutBinding.bind(itemView);
        }
    }
}
