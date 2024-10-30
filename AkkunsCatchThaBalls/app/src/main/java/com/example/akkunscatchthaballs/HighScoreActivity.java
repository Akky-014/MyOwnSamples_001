package com.example.akkunscatchthaballs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.akkunscatchthaballs.data.HighScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity {

    // RecyclerViewのインスタンス
    private RecyclerView recyclerView;
    private HighScoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_high_score);

        // RecyclerViewの設定
        recyclerView = findViewById(R.id.highScoreRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 非同期通信を開始
        fetchHighScores();
    }

    // Kotlinの通信クラスを使って非同期通信を開始
    private void fetchHighScores() {
        // 静的に `NetworkUtils` にアクセス
        NetworkUtils.fetchTop5(response -> {
            runOnUiThread(() -> {
                // JSONレスポンスをDTOに変換
                List<HighScore> highScoreList = parseJsonToHighScoreList(response);

                // 変換したデータをRecyclerViewに表示
                displayHighScores(highScoreList);
            });
            return null;
        });
    }

    // JSONレスポンスを解析し、DTOに詰めるメソッド
    private List<HighScore> parseJsonToHighScoreList(String jsonResponse) {
        // Gsonを使用してJSONをHighScoreのリストに変換
        Gson gson = new Gson();
        Type listType = new TypeToken<List<HighScore>>() {}.getType();
        return gson.fromJson(jsonResponse, listType);
    }

    // RecyclerViewにデータを表示するメソッド
    private void displayHighScores(List<HighScore> highScoreList) {
        // RecyclerViewのアダプターを設定
        adapter = new HighScoreAdapter(highScoreList);
        recyclerView.setAdapter(adapter);
    }

    public void back(View view) {
        finish();
    }
}