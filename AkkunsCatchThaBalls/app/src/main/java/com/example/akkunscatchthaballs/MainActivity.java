package com.example.akkunscatchthaballs;


import static com.example.akkunscatchthaballs.data.SettingKt.settingMain;
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

import com.example.akkunscatchthaballs.data.Setting;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLabel;
    private TextView startLabel;
    private ImageView box;
    private ImageView isaki;
    private ImageView tuna;
    private ImageView eel;
    private ImageView bomb;
    private ImageView fukuro;
    private ImageView kan;

    // サイズ
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;

    // 位置
    private float boxY;
    private float isakiX;
    private float isakiY;
    private float tunaX;
    private float tunaY;
    private float eelX;
    private float eelY;
    private float bombX;
    private float bombY;
    private float kanX;
    private float kanY;
    private float fukuroX;
    private float fukuroY;

    // スピード
    private int boxSpeed;
    private int isakiSpeed;
    private int tunaSpeed;
    private int eelSpeed;
    private int bombSpeed;
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

        // セッティングインスタンスを作成
        Setting setting = settingMain();

        Typeface ronde = Typeface.createFromAsset(getAssets(), "pugnomincho-mini.otf");

        soundPlayer = new SoundPlayer(this);

        scoreLabel = findViewById(R.id.scoreLabel);
        startLabel = findViewById(R.id.startLabel);
        startLabel.setTypeface(ronde);

        box = findViewById(R.id.box);
        isaki = findViewById(R.id.isaki);
        tuna = findViewById(R.id.tuna);
        eel = findViewById(R.id.eel);
        bomb = findViewById(R.id.bomb);
        fukuro = findViewById(R.id.fukuro);
        kan = findViewById(R.id.kan);

        // Screen Size
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth = size.x;
        screenHeight = size.y;

        // スピードをセット
        boxSpeed = Math.round(screenHeight / setting.getBoxSpeed());
        isakiSpeed = Math.round(screenWidth / setting.getIsakiSpeed());
        tunaSpeed = Math.round(screenWidth / setting.getTunaSpeed());
        eelSpeed = Math.round(screenWidth / setting.getEelSpeed());
        bombSpeed = Math.round(screenWidth / setting.getBombSpeed());
        fukuroSpeed = Math.round(screenWidth / setting.getFukuroSpeed());
        kanSpeed = Math.round(screenWidth / setting.getKanSpeed());

        isaki.setX(setting.getXyDefault());
        isaki.setY(setting.getXyDefault());
        tuna.setX(setting.getXyDefault());
        tuna.setY(setting.getXyDefault());
        eel.setX(setting.getXyDefault());
        eel.setY(setting.getXyDefault());
        bomb.setX(setting.getXyDefault());
        bomb.setY(setting.getXyDefault());
        fukuro.setX(setting.getXyDefault());
        fukuro.setY(setting.getXyDefault());
        kan.setX(setting.getXyDefault());
        kan.setY(setting.getXyDefault());

        scoreLabel.setText("Score : 0");

        // カウントダウン用 TextView の取得
        countdownTextView = findViewById(R.id.countdown_text_view);

        // カウントダウンタイマーの作成
        countDownTimer = new MyCountDownTimer(setting.getCountDownTimeMax(), 1000, countdownTextView);
    }

    public void changePos() {

        hitCheck();

        // isaki
        isakiX -= isakiSpeed;
        if (isakiX < 0) {
            isakiX = screenWidth + 20;
            isakiY = (float)Math.floor(Math.random() * (frameHeight - isaki.getHeight()));
        }
        isaki.setX(isakiX);
        isaki.setY(isakiY);

        // tuna
        tunaX -= tunaSpeed;
        if (tunaX < 0) {
            tunaX = screenWidth + 3000;
            tunaY = (float)Math.floor(Math.random() * (frameHeight - tuna.getHeight()));
        }
        tuna.setX(tunaX);
        tuna.setY(tunaY);

        // bomb
        bombX -= bombSpeed;
        if (bombX < 0) {
            bombX = screenWidth + 5000;
            bombY = (float)Math.floor(Math.random() * (frameHeight - bomb.getHeight()));
        }
        bomb.setX(bombX);
        bomb.setY(bombY);

        // eel
        eelX -= eelSpeed;
        if (eelX < 0) {
            eelX = screenWidth + 9000;
            eelY = (float)Math.floor(Math.random() * (frameHeight - eel.getHeight()));
        }
        eel.setX(eelX);
        eel.setY(eelY);

        // kan
        kanX -= kanSpeed;
        if (kanX < 0) {
            kanX = screenWidth + 1000;
            kanY = (float)Math.floor(Math.random() * (frameHeight - tuna.getHeight()));
        }
        kan.setX(kanX);
        kan.setY(kanY);

        // fukuro
        fukuroX -= fukuroSpeed;
        if (fukuroX < 0) {
            fukuroX = screenWidth + 2000;
            fukuroY = (float)Math.floor(Math.random() * (frameHeight - tuna.getHeight()));
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

        // isaki
        float isakiCenterX = isakiX + isaki.getWidth() / 2;
        float isakiCenterY = isakiY + isaki.getHeight() / 2;
        if (hitStatus(isakiCenterX, isakiCenterY)) {
            isakiX = -10.0f;
            score += 10;
            soundPlayer.playHitSound();
        }

        // tuna
        float tunaCenterX = tunaX + tuna.getWidth() / 2;
        float tunaCenterY = tunaY + tuna.getHeight() / 2;
        if (hitStatus(tunaCenterX, tunaCenterY)) {
            tunaX = -10.0f;
            score += 20;
            soundPlayer.playHitSound();
        }

        // eel
        float eelCenterX = eelX + eel.getWidth() / 2;
        float eelCenterY = eelY + eel.getHeight() / 2;

        if (hitStatus(eelCenterX, eelCenterY)) {
            eelX = -10.0f;
            score += 50;
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

        // bomb
        float bombCenterX = bombX + bomb.getWidth() / 2;
        float bombCenterY = bombY + bomb.getHeight() / 2;

        if (hitStatus(bombCenterX, bombCenterY)) {
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