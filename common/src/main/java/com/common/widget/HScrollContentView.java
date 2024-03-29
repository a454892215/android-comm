package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.comm.L;
import com.common.utils.LogUtil;

/**
 * Author: L
 * 2018/12/26
 * Description:
 */
@SuppressWarnings("unused")
public class HScrollContentView extends View {

    private VelocityTracker velocityTracker;
    private ValueAnimator anim;
    private final float min_scroll_unit;
    public Scroller mScroller;
    private final int maxVelocity;
    private ValueAnimator flingAnim;
    protected boolean isTouching;

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
        maxVelocity = (int) L.dp_1 * 1500;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
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
                isTouching = true;
                if (flingAnim != null) flingAnim.cancel();
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
            case MotionEvent.ACTION_UP:
                isTouching = false;
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
                scrollXBy(-Math.round(dx));
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                if (Math.abs(xScrollSum) > min_scroll_unit / 2) {
                    velocityTracker.computeCurrentVelocity(2000, maxVelocity);
                    float xVelocity = velocityTracker.getXVelocity();
                    mScroller.abortAnimation();
                    //  mScroller.fling(mScroller.getFinalX(), 0, -Math.round(xVelocity), 0, 0, (int) maxScrollWidth, 0, 0);
                    if (flingAnim != null) flingAnim.cancel();
                    flingAnim = ValueAnimator.ofFloat(getFlingDistance(xVelocity), 0);
                    flingAnim.setDuration(Math.abs((int) xVelocity) / 2);
                    flingAnim.setInterpolator(new DecelerateInterpolator());
                    flingAnim.addUpdateListener(animation -> {
                        float value = (float) animation.getAnimatedValue();
                        scrollXBy(-value);
                    });
                    flingAnim.start();
                    invalidate();
                } else {
                    LogUtil.d("  xScrollSum:" + xScrollSum + "  min_scroll_unit:" + min_scroll_unit);
                }
                return false;
        }
        return true;
    }

    public float getMaxScrollWidth() {
        return maxScrollWidth;
    }

    public void setMaxScrollWidth(float width) {
        this.maxScrollWidth = width;
    }

    protected float maxScrollWidth;

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //  test(canvas);
    }

    protected float getFlingDistance(float xVelocity) {
        return xVelocity / 100 * L.dp_1;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //  scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            if (onScrollListener != null) {
                onScrollListener.onScroll(mScroller.getFinalX());
            }
            invalidate();
        }
        super.computeScroll();
    }

    protected boolean scrollEnable = true;

    public void setScrollEnable(boolean scrollEnable) {
        this.scrollEnable = scrollEnable;
    }

    public boolean isScrollEnable() {
        return scrollEnable;
    }

    public void scrollXBy(float dx) {
        if (!scrollEnable) return;
        if (mScroller.getFinalX() + dx < 0) {//getFinalX 避免延迟
            dx = 0f - mScroller.getFinalX();
        }
        if (mScroller.getFinalX() + dx > maxScrollWidth) {
            dx = maxScrollWidth - mScroller.getFinalX();
        }
        mScroller.startScroll(mScroller.getFinalX(), 0, (int) dx, 0, 180);
        invalidate();
    }

    public void scrollToX(float x) {
        if (!scrollEnable) return;
        if (x < 0) x = 0;
        if (x > maxScrollWidth) x = maxScrollWidth;
        dx = x - mScroller.getFinalX();
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    private OnScrollListener onScrollListener;

    public interface OnScrollListener {
        void onScroll(float currentX);
    }
}