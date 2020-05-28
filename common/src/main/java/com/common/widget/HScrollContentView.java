package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.comm.L;

/**
 * Author: L
 * 2018/12/26
 * Description:
 */
public class HScrollContentView extends View {

    private VelocityTracker velocityTracker;
    private ValueAnimator anim;
    private float min_scroll_unit;
    private Scroller mScroller;
    private int maxVelocity;
    private TextPaint paint;

    public HScrollContentView(Context context) {
        this(context, null);
    }

    public HScrollContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HScrollContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        maxVelocity = (int) L.dp_1 * 3500;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        paint = new TextPaint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(L.dp_1 * 8);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev);
        return true;
    }

    private void onInterceptTouchEvent(MotionEvent ev) {
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
            onTouchEvent(ev);
        }
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
                    //  LogUtil.d("===========xVelocity:" + xVelocity + " maxVelocity:" + maxVelocity);
                    mScroller.abortAnimation();
                    mScroller.fling(mScroller.getFinalX(), 0, -Math.round(xVelocity), 0, 0, (int) maxScrollWidth, 0, 0);
                    invalidate();
                }
                return false;
        }
        return true;
    }


    public void setMaxScrollWidth(float maxScrollWidth) {
        this.maxScrollWidth = maxScrollWidth;
    }

    private float maxScrollWidth;

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        test(canvas);
    }

    private void test(Canvas canvas) {

        float itemWidth = L.dp_1 * 40;
        int size = 1000;
        maxScrollWidth = itemWidth * size - getMeasuredWidth();
        for (int i = 0; i < size; i++) {
            float itemHeight = L.dp_1 * 20;
            float left = i * itemWidth;
            float top = L.dp_1 * 20;
            paint.setColor(i % 2 == 0 ? Color.RED : Color.GREEN);
            canvas.drawRect(left, top, left + itemWidth, top + itemHeight, paint);
            canvas.drawText(i + "", left + itemWidth / 2f, top, paint);
        }
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

    private void executeScrollXBy(float dx) {
        if (mScroller.getFinalX() + dx < 0) {//getFinalX 避免延迟
            dx = 0 - mScroller.getFinalX();
        }
        if (mScroller.getFinalX() + dx > maxScrollWidth) {
            dx = maxScrollWidth - mScroller.getFinalX();
        }
        mScroller.startScroll(mScroller.getFinalX(), 0, (int) dx, 0, 180);
        invalidate();
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