package com.common.comm;

import android.os.CountDownTimer;

import com.common.base.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Author:  Liu Pan
 * CreateDate: 2018/9/7 10:23
 * Description: No
 */
@SuppressWarnings("unused")
public class MyCountDownTimer extends CountDownTimer {
    private OnTickListener onTickListener;
    private int executeCount = 0;
    private int maxExecuteCount = executeCount - 1;//最大执行次数,初始值要小于count最小值
    private WeakReference<BaseActivity> weakReference;
    private boolean isBingActivity = false;

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public MyCountDownTimer(int maxExecuteCount, long countDownInterval) {
        super(Long.MAX_VALUE, countDownInterval);
        this.maxExecuteCount = maxExecuteCount;
    }

    //执行millisInFuture/countDownInterval或者+1次 第一次执行的时候 millisUntilFinished 会略微低于millisInFuture
    @Override
    public void onTick(long millisUntilFinished) {
        if (isBingActivity) {
            BaseActivity baseActivity = weakReference.get();
            if (baseActivity != null && baseActivity.isShowing()) {
                onTickListener.onTick(millisUntilFinished, ++executeCount);
            } else {
                this.cancel();
            }
        } else {
            onTickListener.onTick(millisUntilFinished, ++executeCount);
        }
        if (executeCount == maxExecuteCount) this.cancel();
    }

    //当剩余时间少于countDownInterval 时候 被调用
    @Override
    public void onFinish() {
    }

    public void setOnTickListener(OnTickListener onTickListener) {
        this.onTickListener = onTickListener;
    }

    public MyCountDownTimer bindActivity(BaseActivity baseActivity) {
        this.isBingActivity = true;
        weakReference = new WeakReference<>(baseActivity);
        return this;
    }
}
