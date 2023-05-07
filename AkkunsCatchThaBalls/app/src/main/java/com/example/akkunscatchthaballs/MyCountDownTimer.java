package com.example.akkunscatchthaballs;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.Locale;

public class MyCountDownTimer extends CountDownTimer {
    private TextView countdownTextView;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView countdownTextView) {
        super(millisInFuture, countDownInterval);
        this.countdownTextView = countdownTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long minute = millisUntilFinished / 1000 / 60;
        long second = (millisUntilFinished / 1000) % 60;
        countdownTextView.setText(String.format(Locale.getDefault(), "Time : %02d:%02d", minute, second));
    }

    @Override
    public void onFinish() {
        countdownTextView.setText("Time : 00:00");
        ((MainActivity) countdownTextView.getContext()).Gameover();
    }
}

