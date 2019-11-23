package com.common.comm.timer;

import android.os.CountDownTimer;

import com.common.base.BaseActivity;

public class MyTimer extends CountDownTimer {

    public MyTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setOnTickListener(OnTickListener2 onTickListener) {
        this.onTickListener = onTickListener;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        if (onTickListener != null) {
            onTickListener.onTick(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {
        if (onTickListener != null) {
            onTickListener.onTick(0);
        }
    }

    private OnTickListener2 onTickListener;

    public void bindActivity(BaseActivity baseActivity) {
        baseActivity.addOnPauseListener(this::cancel);
    }
}
