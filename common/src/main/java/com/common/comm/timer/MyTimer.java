package com.common.comm.timer;

import android.os.CountDownTimer;

import com.common.base.BaseActivity;

public class MyTimer extends CountDownTimer {

    private int executeCount = 0;

    public MyTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setOnTickListener(OnTickListener2 onTickListener) {
        this.onTickListener = onTickListener;
    }

    /**
     * @param millisUntilFinished 值域（millisInFuture，0]
     */
    @Override
    public void onTick(long millisUntilFinished) {
        executeCount++;
        if (onTickListener != null) {
            onTickListener.onTick(millisUntilFinished, executeCount);
        }
    }

    @Override
    public void onFinish() {
        executeCount++;
        if (onTickListener != null) {
            onTickListener.onTick(0, executeCount);
        }
    }

    private OnTickListener2 onTickListener;

    public void bindActivity(BaseActivity baseActivity) {
        baseActivity.addOnPauseListener(this::cancel);
    }
}
