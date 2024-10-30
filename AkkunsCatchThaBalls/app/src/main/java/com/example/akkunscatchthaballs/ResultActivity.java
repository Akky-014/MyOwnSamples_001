package com.example.akkunscatchthaballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel = findViewById(R.id.scoreLabel);
        TextView highScoreLabel = findViewById(R.id.highScoreLabel);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreLabel.setText(score + "");

        SharedPreferences sharedPreferences = getSharedPreferences("GAME_DATA", MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("HIGH_SCORE", 0);

        if (score > highScore) {
            highScoreLabel.setText("High Score : " + score);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.apply();

        } else {
            highScoreLabel.setText("High Score : " + highScore);
        }
    }

    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // スコアをセーブするボタンがクリックされたときの処理
    public void saveScore(View view) {
        // scoreLabel からスコアの値を取得
        TextView scoreLabel = findViewById(R.id.scoreLabel);
        String score = scoreLabel.getText().toString();

        // Intent を使用して scoreSaveActivity にスコアを渡す
        Intent intent = new Intent(this, ScoreSaveActivity.class);
        intent.putExtra("score", score); // スコアを Intent に追加
        startActivity(intent); // ScoreSaveActivity を起動
    }

    public void backToStart(View view) {
        startActivity(new Intent(getApplicationContext(), StartActivity.class));
    }
}