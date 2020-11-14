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
import com.common.utils.LogUtil;
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
        maxVelocity = (int) L.dp_1 * 1000;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
    }

    private List<ViewItem> testTotalData = new ArrayList<>();

    float startX;
    float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // LogUtil.d("====dispatchTouchEvent======:");
        if (getChildCount() > 0) {
            maxScrollHeight = getChildAt(0).getHeight();
        }

        onInterceptTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear();
        }
        velocityTracker.addMovement(ev);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // LogUtil.d("====onInterceptTouchEvent======:");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (flingAnim != null) flingAnim.cancel();
                startX = ev.getRawX();
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float currentX = ev.getRawX();
                float currentY = ev.getRawY();
                float dx = currentX - startX;
                dy = currentY - startY;
                startX = currentX;
                startY = currentY;
                break;
        }
//        if (orientation == orientation_vertical) {
//
//        }
        onTouchEvent(ev);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //  super.onTouchEvent(ev);
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
                return true;
        }
        return true;
    }


    protected float maxScrollHeight = 0;

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
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
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


    private static final float minY = 0;

    private void executeScrollY(float dy) {
        if (!scrollEnable) return;
        LogUtil.d("============getFinalY:" + mScroller.getFinalY());
        if (mScroller.getFinalY() + dy < minY) {//getFinalX 避免延迟
            dy = minY - mScroller.getFinalY();
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