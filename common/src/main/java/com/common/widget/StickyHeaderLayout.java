package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.comm.L;
import com.common.widget.entity.ViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: L
 * 2020/11/24
 * Description:
 */
@SuppressWarnings("unused")
public class StickyHeaderLayout extends LinearLayout {

    private VelocityTracker velocityTracker;
    private ValueAnimator anim;
    private float min_scroll_unit;
    protected Scroller mScroller;
    private int maxVelocity;
    private ValueAnimator flingAnim;
    private float dy;

    public StickyHeaderLayout(Context context) {
        this(context, null);
    }

    public StickyHeaderLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public StickyHeaderLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        maxVelocity = (int) L.dp_1 * 1500;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private List<ViewItem> testTotalData = new ArrayList<>();

    float startX;
    float startY;
    int orientation = 0;
    int orientation_vertical = 1;
    int orientation_horizontal = 2;

    private static final int max_compute_times = 3;
    private int compute_times = 0;
    private float xScrollSum;
    private float yScrollSum;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev);
        return true;
    }

    private void preHandleMotionEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
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
                float dx = currentX - startX;
                dy = currentY - startY;
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
                executeScrollY(-Math.round(dy));
                break;
            case MotionEvent.ACTION_UP:
                performClick();
                if (Math.abs(dy) > min_scroll_unit / 2) {
                    velocityTracker.computeCurrentVelocity(2000, maxVelocity);
                    float yVelocity = velocityTracker.getYVelocity();
                    mScroller.abortAnimation();
                    if (flingAnim != null) flingAnim.cancel();
                    flingAnim = ValueAnimator.ofFloat(getFlingDistance(yVelocity), 0);
                    flingAnim.setDuration(Math.abs((int) yVelocity) / 2);
                    flingAnim.setInterpolator(new DecelerateInterpolator());
                    flingAnim.addUpdateListener(animation -> {
                        float value = (float) animation.getAnimatedValue();
                        executeScrollY(-value);
                    });
                    flingAnim.start();
                    invalidate();
                }
                return false;
        }
        return true;
    }

    public float getMaxScrollHeight() {
        return maxScrollHeight;
    }

    protected float maxScrollHeight;

    protected float getFlingDistance(float yVelocity) {
        return yVelocity / 100 * L.dp_1;
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
                onScrollListener.onScroll(mScroller.getFinalY());
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


    private void executeScrollY(float dy) {
        if (!scrollEnable) return;
        if (mScroller.getFinalY() + dy < 0) {//getFinalX 避免延迟
            dy = 0f - mScroller.getFinalY();
        }
        if (mScroller.getFinalY() + dy > maxScrollHeight) {
            dy = maxScrollHeight - mScroller.getFinalY();
        }
        mScroller.startScroll(0, mScroller.getFinalY(), 0, (int) dy, 180);
        invalidate();
    }

    public void executeScrollToY(float y) {
        if (!scrollEnable) return;
        if (y < 0) y = 0;
        if (y > maxScrollHeight) y = maxScrollHeight;
        dy = y - mScroller.getFinalY();
        mScroller.startScroll(0, mScroller.getFinalY(), 0, (int) dy, 180);
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