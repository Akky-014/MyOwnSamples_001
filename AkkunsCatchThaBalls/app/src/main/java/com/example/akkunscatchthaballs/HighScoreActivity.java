package com.example.akkunscatchthaballs;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class HighScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_high_score);

        // 非同期通信を開始
        fetchHighScores();
    }

    // Kotlinの通信クラスを使って非同期通信を開始
    private void fetchHighScores() {
        // 静的に `NetworkUtils` にアクセス
        NetworkUtils.fetchTop3(response -> {
            runOnUiThread(() -> {
                System.out.println(response);
            });
            return null;
        });
    }
}