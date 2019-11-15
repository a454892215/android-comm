package com.common.comm.timer;

import com.common.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Author:  L
 * CreateDate: 2018/9/7 10:23
 * Description: No
 */
@SuppressWarnings("unused")
public class MyCountDownTimer extends CountDownTimer {
    private OnTickListener onTickListener;
    private OnTickListener onLastTickListener;
    private int executeCount = 0;
    private int maxExecuteCount;//最大执行次数,初始值要小于count最小值
    private WeakReference<BaseActivity> weakReference;
    private boolean isBingActivity = false;
    private long countDownInterval;

    public MyCountDownTimer(int maxExecuteCount, long countDownInterval) {
        super(Long.MAX_VALUE, countDownInterval);
        this.maxExecuteCount = maxExecuteCount;
        this.countDownInterval = countDownInterval;
    }

    //执行millisInFuture/countDownInterval或者+1次 第一次执行的时候 millisUntilFinished 会略微低于millisInFuture
    @Override
    public void onTick(long millisUntilFinished) {
        long finishedTime = Long.MAX_VALUE - millisUntilFinished; //完成时间
        long remainTime = maxExecuteCount * countDownInterval - finishedTime;//剩余时间

        if (isBingActivity) {
            BaseActivity baseActivity = weakReference.get();
            if (baseActivity != null && baseActivity.isShowing()) {
                if (onTickListener != null) {
                    onTickListener.onTick(remainTime, ++executeCount);
                }
            } else {
                this.cancel();
            }
        } else {
            executeCount++;
            if (onTickListener != null) {
                onTickListener.onTick(remainTime, executeCount);
            }
            if (onLastTickListener != null && executeCount >= maxExecuteCount) {
                onLastTickListener.onTick(remainTime, executeCount);
            }

        }
        if (executeCount >= maxExecuteCount) this.cancel();
    }

    //当剩余时间少于countDownInterval 时候 被调用
    @Override
    public void onFinish() {
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    public void setOnLastTickListener(OnTickListener onLastTickListener) {
        this.onLastTickListener = onLastTickListener;
    }

    public MyCountDownTimer bindActivity(BaseActivity baseActivity) {
        this.isBingActivity = true;
        weakReference = new WeakReference<>(baseActivity);
        return this;
    }

}
