package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

import com.common.R;
import com.common.utils.LogUtil;

/**
 * Author: Pan
 * 2018/12/23
 * Description:
 */
public class HorizontalScrollLayout extends FrameLayout {

    private ValueAnimator anim;
    private float min_scroll_unit;

    public HorizontalScrollLayout(Context context) {
        this(context, null);
    }

    public HorizontalScrollLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_1);
    }

    public HorizontalScrollLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private VelocityTracker velocityTracker;

    float startX;
    float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        velocityTracker.addMovement(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getRawX();
                float currentY = ev.getRawY();
                float dx = currentX - startX;
                float dy = currentY - startY;
                startX = currentX;
                startY = currentY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    LogUtil.d("==========水平 滑动========dx:" + dx + "  dy:" + dy + "  getScrollX():" + getScrollX());

                    int abs_dx = Math.round(-dx);
                    if (abs_dx > min_scroll_unit) {
                        int times = Math.round(abs_dx / min_scroll_unit) + 1;
                        for (int i = 0; i < times; i++) {
                            postDelayed(() -> executeScrollXBy(Math.round(min_scroll_unit)), 10 * i);
                        }
                    } else {
                        executeScrollXBy(Math.round(-dx));
                    }
                } else {
                    LogUtil.e("==========垂直 滑动========dx:" + dx + "  dy:" + dy + " ");
                }
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1);
                float xVelocity = velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 0.5) {
                    startScroll(xVelocity);
                }
                //     LogUtil.d("=====ACTION_UP=====水平 滑动========dx:" + xVelocity);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void startScroll(float xVelocity) {
        if (anim != null) {
            anim.end();
            anim.cancel();
        }
        anim = ValueAnimator.ofFloat(xVelocity * 5, 0);
        anim.setDuration(200);
        anim.addUpdateListener(animation -> {
            Float currentValue = (Float) animation.getAnimatedValue();
            executeScrollXBy(Math.round(-currentValue));
        });
        anim.start();
    }

    private int scrollX;

    private void executeScrollXBy(int x) {
        x = Math.round(x);
        scrollX += x;
        scrollX = scrollX > maxScrollWidth ? maxScrollWidth : scrollX;
        scrollX = scrollX < 0 ? 0 : scrollX;
        //  LogUtil.debug("executeScrollBy scrollX:" + scrollX);
        scrollTo(scrollX, 0);
    }


    private int maxScrollWidth;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        maxScrollWidth = getChildAt(0).getWidth() - getWidth();
        LogUtil.debug("onLayout maxScrollWidth:" + maxScrollWidth + "  ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }

        if (anim != null) {
            anim.cancel();
            anim = null;
        }
    }
}