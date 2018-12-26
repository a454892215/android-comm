package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.FrameLayout;

import com.common.R;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class HorizontalScrollRV extends FrameLayout {

    private ValueAnimator anim;
    private float min_scroll_unit;

    public HorizontalScrollRV(Context context) {
        this(context, null);
    }

    public HorizontalScrollRV(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        //  float slop = getResources().getDimension(R.dimen.dp_5);
    }

    public HorizontalScrollRV(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_recycler_rv, this);
        RecyclerView recyclerView = findViewById(R.id.rv_horizontal);
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            float startX;
            float startY;
            float dxAll;
            float dyAll;
            int orientation = 0;
            int orientation_vertical = 1;
            int orientation_horizontal = 2;

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent ev) {
                velocityTracker.addMovement(ev);
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
                        dxAll += dx;
                        dyAll += dy;
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
                        dxAll = 0;
                        dyAll = 0;
                        break;
                }
                return false;
            }

            float lastX;
            float dx;

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent ev) {
                velocityTracker.addMovement(ev);
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // LogUtil.d("==ACTION_MOVE=水平滑动===dx:");
                        if (lastX == 0) {
                            lastX = ev.getRawX();
                        } else {
                            float startX = ev.getRawX();
                            dx = startX - lastX;
                            lastX = startX;
                            int abs_dx = Math.round(-dx);
                            if (abs_dx > min_scroll_unit) {
                                int times = Math.round(abs_dx / min_scroll_unit) + 1;
                                for (int i = 0; i < times; i++) {
                                    postDelayed(() -> executeScrollXBy(Math.round(min_scroll_unit)), 10 * i);
                                }
                            } else {
                                executeScrollXBy(Math.round(-dx));
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        //  velocityTracker.computeCurrentVelocity(1);
                        //     float xVelocity = velocityTracker.getXVelocity();
                        if (Math.abs(dx) > min_scroll_unit/2) startScroll(-dx);
                        lastX = 0;
                        break;
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }

    private VelocityTracker velocityTracker;

    private void startScroll(float xVelocity) {
        if (anim != null) {
            anim.end();
        }
        float velocity = MathUtil.absClamp(xVelocity, min_scroll_unit * 2, min_scroll_unit * 5);
        LogUtil.d("==ACTION_UP=水平滑动=== 原值 xVelocity:" + xVelocity + "  velocity  :" + velocity);
        anim = ValueAnimator.ofFloat(velocity, 0);
        anim.setDuration(400);
        anim.addUpdateListener(animation -> {
            Float currentValue = (Float) animation.getAnimatedValue();
            executeScrollXBy(Math.round(currentValue));
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