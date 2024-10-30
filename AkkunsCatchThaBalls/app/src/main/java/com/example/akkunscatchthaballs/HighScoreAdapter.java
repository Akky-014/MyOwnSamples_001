package com.example.akkunscatchthaballs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akkunscatchthaballs.data.HighScore;

import java.util.List;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder> {

    private final List<HighScore> highScoreList;

    public HighScoreAdapter(List<HighScore> highScoreList) {
        this.highScoreList = highScoreList;
    }

    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_high_score, parent, false);
        return new HighScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {
        HighScore highScore = highScoreList.get(position);
        holder.playerName.setText((position + 1) + ". " + highScore.getName());
        holder.playerScore.setText(String.valueOf(highScore.getHighScore()));
    }

    @Override
    public int getItemCount() {
        return highScoreList.size();
    }

    public static class HighScoreViewHolder extends RecyclerView.ViewHolder {
        TextView playerName, playerScore;

        public HighScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            playerScore = itemView.findViewById(R.id.playerScore);
        }
    }
}