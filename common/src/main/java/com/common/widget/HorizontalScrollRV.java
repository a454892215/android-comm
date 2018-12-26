package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.common.R;
import com.common.utils.LogUtil;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class HorizontalScrollRV extends FrameLayout {

    private ValueAnimator anim;
    private float min_scroll_unit;
    private Scroller mScroller;

    public HorizontalScrollRV(Context context) {
        this(context, null);
    }

    public HorizontalScrollRV(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        //  float slop = getResources().getDimension(R.dimen.dp_5);
    }

    public HorizontalScrollRV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_recycler_rv, this);
        RecyclerView recyclerView = findViewById(R.id.rv_horizontal);

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            float startX;
            float startY;
            int orientation = 0;
            int orientation_vertical = 1;
            int orientation_horizontal = 2;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = ev.getRawX();
                        startY = ev.getRawY();
                        orientation = 0;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentX = ev.getRawX();
                        float currentY = ev.getRawY();
                        float dx = currentX - startX;
                        float dy = currentY - startY;
                        startX = currentX;
                        startY = currentY;
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (orientation == 0) orientation = orientation_horizontal;
                            //避免垂直滑动事件流 执行水平滑动
                            if (orientation == orientation_horizontal) return true;
                        } else {
                            if (orientation == 0) orientation = orientation_vertical;
                        }
                        LogUtil.d("============================垂直滑动===:" + " dx:" + dx + " dy:" + dy);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }

            float lastX;
            float dx;

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // LogUtil.d("==ACTION_MOVE=水平滑动===dx:");
                        if (lastX == 0) {
                            lastX = ev.getRawX();
                        } else {
                            float startX = ev.getRawX();
                            dx = startX - lastX;
                            lastX = startX;
                            executeScrollXBy(-Math.round(dx));
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(dx) > min_scroll_unit / 2) {
                            int dx_i = Math.round(dx);
                            mScroller.forceFinished(true);
                            mScroller.abortAnimation();
                            mScroller.fling(getScrollX(), 0, -dx_i * 30, 0, 0, maxScrollWidth, 0, 0);
                            invalidate();
                        }
                        lastX = 0;
                        break;
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
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
        if (mScroller.getCurrX() + dx < 0) {
            dx = 0 - mScroller.getCurrX();
        }
        if (mScroller.getCurrX() + dx > maxScrollWidth) {
            dx = maxScrollWidth - mScroller.getCurrX();
        }
        LogUtil.d(" getScrollX():" + mScroller.getCurrX() + "  dx:" + dx);
        mScroller.forceFinished(true);
        mScroller.abortAnimation();
        mScroller.startScroll(mScroller.getCurrX(), 0, dx, 0, 180);
        invalidate();
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
        if (anim != null) {
            anim.cancel();
            anim = null;
        }
    }
}