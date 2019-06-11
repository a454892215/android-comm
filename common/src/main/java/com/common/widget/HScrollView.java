package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.utils.LogUtil;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * Author: L
 * 2018/12/26
 * Description:
 */
public class HScrollView extends FrameLayout {

    private VelocityTracker velocityTracker;
    private ValueAnimator anim;
    private float min_scroll_unit;
    private Scroller mScroller;
    private int maxVelocity;

    public HScrollView(Context context) {
        this(context, null);
    }

    public HScrollView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        maxVelocity = DensityUtil.dp2px(1500);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean isConsume = onInterceptTouchEvent(ev);
        boolean isChildConsume = true;
        View view = getChildAt(0);
        if (view != null && !isConsume) {
            isChildConsume = view.dispatchTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                velocityTracker.clear();
                break;
        }
        velocityTracker.addMovement(ev);
        if(isConsume){
            return true;
        }else{
            return isChildConsume;
        }
    }


    float startX;
    float startY;
    int orientation = 0;
    int orientation_vertical = 1;
    int orientation_horizontal = 2;

    private static final int max_compute_times = 3;
    private int compute_times = 0;
    private float xScrollSum;
    private float yScrollSum;
    private float dx;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                orientation = 0;
                xScrollSum = 0;
                yScrollSum = 0;
                compute_times = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getRawX();
                float currentY = ev.getRawY();
                dx = currentX - startX;
                float dy = currentY - startY;
                startX = currentX;
                startY = currentY;

                if (compute_times < max_compute_times || (Math.abs(xScrollSum) < min_scroll_unit) && Math.abs(xScrollSum) < min_scroll_unit) {
                    xScrollSum += dx;
                    yScrollSum += dy;
                    compute_times++;
                } else {
                    if (Math.abs(xScrollSum) > Math.abs(yScrollSum)) {
                        if (orientation == 0) orientation = orientation_horizontal;
                    } else if (Math.abs(yScrollSum) > Math.abs(xScrollSum)) {
                        if (orientation == 0) orientation = orientation_vertical;
                    }
                }
                break;
        }
        if (orientation == orientation_horizontal) {
            return onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                executeScrollXBy(-Math.round(dx));
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                if (Math.abs(dx) > min_scroll_unit / 2) {
                    velocityTracker.computeCurrentVelocity(2000, maxVelocity);
                    float xVelocity = velocityTracker.getXVelocity();
                    LogUtil.d("===========xVelocity:" + xVelocity + " maxVelocity:" + maxVelocity);
                    mScroller.abortAnimation();
                    mScroller.fling(mScroller.getFinalX(), 0, -Math.round(xVelocity), 0, 0, maxScrollWidth, 0, 0);
                    invalidate();
                }
                return false;
        }
        return true;
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }

    private void executeScrollXBy(int dx) {
        if (mScroller.getFinalX() + dx < 0) {//getFinalX 避免延迟
            dx = 0 - mScroller.getFinalX();
        }
        if (mScroller.getFinalX() + dx > maxScrollWidth) {
            dx = maxScrollWidth - mScroller.getFinalX();
        }
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, 0, 180);
        invalidate();
    }

    private int maxScrollWidth;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        maxScrollWidth = getChildAt(0).getWidth() - getWidth();
        LogUtil.d("onLayout maxScrollWidth:" + maxScrollWidth + "  ");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (anim != null) {
            anim.cancel();
            anim = null;
        }
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
}