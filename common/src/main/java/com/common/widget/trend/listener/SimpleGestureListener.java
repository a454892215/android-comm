package com.common.widget.trend.listener;

import android.animation.ValueAnimator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import com.common.utils.LogUtil;
import com.common.widget.trend.TrendChartView;

public class SimpleGestureListener extends GestureDetector.SimpleOnGestureListener {

    private TrendChartView view;
    private ValueAnimator flingAnimator;

    public SimpleGestureListener(TrendChartView view) {
        this.view = view;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        stopFlingAnim();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int pointerCount = e2.getPointerCount();
        if (pointerCount > 1) {
            return false;
        }
        scrollByWithBorderline(distanceX);
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        fling(velocityX);
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        LogUtil.d("=========onSingleTapUp===========");
        return true;
    }

    private void scrollByWithBorderline(float distanceDX) {
        int dx = Math.round(distanceDX);
        if (view.getScrollX() + dx <= 0) {
            dx = -view.getScrollX();
        }

        if (view.getScrollX() + dx >= view.getMaxVisibleWidth()) {
            dx = Math.round(view.getMaxVisibleWidth() - view.getScrollX());
        }
        view.scrollBy(dx, 0);
    }

    private void fling(float velocityX) {
        int flingDuring = (int) Math.abs(velocityX / 8f);
        float distanceX = -velocityX * (flingDuring / 1000f);
        //LogUtil.d("=========onFling===========velocityX:" + velocityX + "    distanceX:" + distanceX + " flingDuring:" + flingDuring);
        stopFlingAnim();
        flingAnimator = ValueAnimator.ofFloat(distanceX, 0);
        flingAnimator.setDuration(flingDuring);
        flingAnimator.setInterpolator(new DecelerateInterpolator());
        flingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private float lastTargetDistance;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                if (lastTargetDistance != 0) {
                    float distanceDX = value - lastTargetDistance;
                    scrollByWithBorderline(-distanceDX);
                }
                lastTargetDistance = value;
            }
        });
        flingAnimator.start();
    }

    private void stopFlingAnim() {
        if (flingAnimator != null) {
            flingAnimator.end();
            flingAnimator.cancel();
        }
    }
}
