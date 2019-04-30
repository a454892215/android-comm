package com.common.widget.trend.listener;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.common.utils.LogUtil;

public class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

    private View view;

    public SimpleGestureListener(View view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        LogUtil.d("=========onDown===========");
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int pointerCount = e2.getPointerCount();
        if (pointerCount > 1) {
            return false;
        }
        int dx = scrollByWithBorderline(distanceX);
        LogUtil.d("=========onScroll===========dx:" + dx);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        LogUtil.d("=========onFling===========:" + velocityX);
        float distanceX = -velocityX / 200;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distanceX, 0);
        valueAnimator.setDuration(120);
        valueAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            LogUtil.d("");
        });
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.d("=========onSingleTapUp===========");
        return true;
    }

    private int scrollByWithBorderline(float distanceX) {
        int dx = Math.round(distanceX);
        if (view.getScrollX() + dx <= 0) {
            dx = -view.getScrollX();
        }
        view.scrollBy(dx, 0);
        return dx;
    }
}
