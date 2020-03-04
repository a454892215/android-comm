package com.common.comm.timer;

import android.os.CountDownTimer;

import com.common.base.BaseActivity;

public class MyTimer extends CountDownTimer {

    private int executeCount = 0;
    private long hasExecuteTime = 0;

    private long millisInFuture;
    public MyTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.millisInFuture = millisInFuture;
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    /**
     * @param millisUntilFinished 值域（millisInFuture，0]
     */
    @Override
    public void onTick(long millisUntilFinished) {
        hasExecuteTime = millisInFuture - millisUntilFinished;
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

    private OnTickListener onTickListener;

    public void bindActivity(BaseActivity baseActivity) {
        baseActivity.addOnPauseListener(this::cancel);
    }


    public long getHasExecuteTime() {
        return hasExecuteTime;
    }


}
