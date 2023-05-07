package com.example.akkunscatchthaballs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView pink;
    private ImageView black;
    private ImageView fukuro;
    private ImageView kan;

    // サイズ
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    // 位置
    private float boxY;
    private float orangeX;
    private float orangeY;
    private float pinkX;
    private float pinkY;
    private float blackX;
    private float blackY;
    private float kanX;
    private float kanY;
    private float fukuroX;
    private float fukuroY;

    // スピード
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;
    private int fukuroSpeed;
    private int kanSpeed;

    // Score
    private int score = 0;

    // Handler & Timer
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    // Status
    private boolean action_flg = false;
    private boolean start_flg = false;

    // Sound
    private SoundPlayer soundPlayer;

    private TextView countdownTextView;
    private MyCountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface ronde = Typeface.createFromAsset(getAssets(), "pugnomincho-mini.otf");

        soundPlayer = new SoundPlayer(this);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        startLabel.setTypeface(ronde);

        box = findViewById(R.id.box);
        orange = findViewById(R.id.orange);
        pink = findViewById(R.id.pink);
        black = findViewById(R.id.black);
        fukuro = findViewById(R.id.fukuro);
        kan = findViewById(R.id.kan);

        // Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        boxSpeed = Math.round(screenHeight / 60f);
        orangeSpeed = Math.round(screenWidth / 60f);
        pinkSpeed = Math.round(screenWidth / 45f);
        blackSpeed = Math.round(screenWidth / 100f);
        fukuroSpeed = Math.round(screenWidth / 80f);
        kanSpeed = Math.round(screenWidth / 90f);

        orange.setX(-80.0f);
        orange.setY(-80.0f);
        pink.setX(-80.0f);
        pink.setY(-80.0f);
        black.setX(-80.0f);
        black.setY(-80.0f);
        fukuro.setX(-80.0f);
        fukuro.setY(-80.0f);
        kan.setX(-80.0f);
        kan.setY(-80.0f);

        scoreLabel.setText("Score : 0");

        // カウントダウン用 TextView の取得
        countdownTextView = findViewById(R.id.countdown_text_view);

        // カウントダウンタイマーの作成
        countDownTimer = new MyCountDownTimer(60000, 1000, countdownTextView);
    }

    public void changePos() {

        hitCheck();

        // Orange
        orangeX -= orangeSpeed;
        if (orangeX < 0) {
            orangeX = screenWidth + 20;
            orangeY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        orange.setX(orangeX);
        orange.setY(orangeY);

        // Black
        blackX -= blackSpeed;
        if (blackX < 0) {
            blackX = screenWidth + 10;
            blackY = (float)Math.floor(Math.random() * (frameHeight - black.getHeight()));
        }
        black.setX(blackX);
        black.setY(blackY);

        // Pink
        pinkX -= pinkSpeed;
        if (pinkX < 0) {
            pinkX = screenWidth + 10000;
            pinkY = (float)Math.floor(Math.random() * (frameHeight - pink.getHeight()));
        }
        pink.setX(pinkX);
        pink.setY(pinkY);

        // kan
        kanX -= kanSpeed;
        if (kanX < 0) {
            kanX = screenWidth + 1000;
            kanY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        kan.setX(kanX);
        kan.setY(kanY);

        // fukuro
        fukuroX -= fukuroSpeed;
        if (fukuroX < 0) {
            fukuroX = screenWidth + 2000;
            fukuroY = (float)Math.floor(Math.random() * (frameHeight - orange.getHeight()));
        }
        fukuro.setX(fukuroX);
        fukuro.setY(fukuroY);

        // Box
        if (action_flg) {
            boxY -= boxSpeed;
        } else {
            boxY += boxSpeed;
        }

        // boxY が 0 より小さくなった場合は青いボックスが frame の外に出ている状態。
        // よってframe から出ないように boxY は 0 未満にならないようする
        if (boxY < 0) boxY = 0;
        // 青いボックスが画面の一番下にある時、boxY は frame の高さからボックスの高さを引いた値にする
        // これ以上 boxY の値が大きくなると画面から出ちゃうから、frameHeight – boxSizeよりも大きくならないようにする
        if (boxY > frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLabel.setText("Score : " + score);
    }

    public void hitCheck() {
        // Orange
        float orangeCenterX = orangeX + orange.getWidth() / 2;
        float orangeCenterY = orangeY + orange.getHeight() / 2;
        if (hitStatus(orangeCenterX, orangeCenterY)) {
            orangeX = -10.0f;
            score += 10;
            soundPlayer.playHitSound();
        }

        // Pink
        float pinkCenterX = pinkX + pink.getWidth() / 2;
        float pinkCenterY = pinkY + pink.getHeight() / 2;

        if (hitStatus(pinkCenterX, pinkCenterY)) {
            pinkX = -10.0f;
            score += 30;
            soundPlayer.playHitSound();
        }

        float kanCenterX = kanX + kan.getWidth() / 2;
        float kanCenterY = kanY + kan.getHeight() / 2;
        if (hitStatus(kanCenterX, kanCenterY)) {
            kanX = -10.0f;
            score -= 10;
            soundPlayer.playDownSound();
        }

        float fukuroCenterX = fukuroX + fukuro.getWidth() / 2;
        float fukuroCenterY = fukuroY + fukuro.getHeight() / 2;
        if (hitStatus(fukuroCenterX, fukuroCenterY)) {
            fukuroX = -10.0f;
            score -= 20;
            soundPlayer.playDownSound();
        }

        // Black
        float blackCenterX = blackX + black.getWidth() / 2;
        float blackCenterY = blackY + black.getHeight() / 2;

        if (hitStatus(blackCenterX, blackCenterY)) {
            // Game Over!
            Gameover();
        }
    }

    public void Gameover() {
        // Game Over!
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

        // 結果画面へ
        soundPlayer.playOverSound();
        Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
        intent.putExtra("SCORE", score);
        startActivity(intent);
    }

    public boolean hitStatus(float centerX, float centerY) {
        return (0 <= centerX && centerX <= boxSize &&
                boxY <= centerY && centerY <= boxY + boxSize) ? true : false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (start_flg == false) {
            start_flg = true;

            FrameLayout frame = findViewById(R.id.frame);
            frameHeight = frame.getHeight();

            boxY = box.getY();
            boxSize = box.getHeight();

            startLabel.setVisibility(View.GONE);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            }, 0, 20);

            countDownTimer.start();

        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                action_flg = true;

            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                action_flg = false;

            }
        }
        return true;
    }

    @Override
    public void onBackPressed() { }
}