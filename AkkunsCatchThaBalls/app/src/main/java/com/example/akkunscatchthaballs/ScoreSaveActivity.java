package com.example.akkunscatchthaballs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ScoreSaveActivity extends AppCompatActivity {

    private String score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_save);

        // Intent からスコアを取得
        score = getIntent().getStringExtra("score");

        // 取得したスコアを表示する
        TextView scoreDisplay = findViewById(R.id.scoreDisplay);
        scoreDisplay.setText("Your Score: " + score);

        // 名前入力フィールドの設定
        EditText nameInput = findViewById(R.id.nameInput);

        // 文字数を10文字に制限
        nameInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});

        // 文字数が10文字を超えた場合のエラーチェック
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 10) {
                    Toast.makeText(ScoreSaveActivity.this, "名前は10文字以内にしてください", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Enterキーが押されたときにキーボードを閉じる
        nameInput.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)) {
                // キーボードを閉じる
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), 0);
                }
                return true;
            }
            return false;
        });
    }

    // OKボタンがクリックされたときの処理
    public void confirmName(View view) {
        // 名前の入力フィールドを取得
        EditText nameInput = findViewById(R.id.nameInput);
        String name = nameInput.getText().toString();

        // 名前が入力されていない場合のチェック
        if (name.isEmpty()) {
            Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show();
            return;
        }

        // サーバーにスコアを保存する
        NetworkUtils.saveScore(name, score, new kotlin.jvm.functions.Function1<String, kotlin.Unit>() {
            @Override
            public kotlin.Unit invoke(String response) {
                // サーバーからのレスポンスに応じてメッセージを表示
                runOnUiThread(() -> {
                    Toast.makeText(ScoreSaveActivity.this, "セーブ完了", Toast.LENGTH_SHORT).show();

                    // 2秒後にホーム画面に戻る
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(ScoreSaveActivity.this, StartActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // 現在のアクティビティを終了
                    }, 2000); // 2秒待つ
                });
                return kotlin.Unit.INSTANCE;
            }
        });
    }
}
