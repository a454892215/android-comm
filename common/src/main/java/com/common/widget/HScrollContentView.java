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
import com.common.utils.LogUtil;
import com.common.widget.entity.ViewItem;

import java.util.ArrayList;
import java.util.List;

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
    private ValueAnimator flingAnim;

    public HScrollContentView(Context context) {
        this(context, null);
    }

    public HScrollContentView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    List<ViewItem> testData = new ArrayList<>();

    public HScrollContentView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        min_scroll_unit = getResources().getDimension(R.dimen.dp_2);
        mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        maxVelocity = (int) L.dp_1 * 1500;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        paint = new TextPaint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(L.dp_1 * 8);

        for (int i = 0; i < 10000; i++) {
            ViewItem viewItem = new ViewItem();
            viewItem.data = i + "";
            viewItem.index = i;
            testData.add(viewItem);
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
                //   if(flingAnim != null) flingAnim.cancel();
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
                    //  mScroller.fling(mScroller.getFinalX(), 0, -Math.round(xVelocity), 0, 0, (int) maxScrollWidth, 0, 0);
                    if (flingAnim != null) flingAnim.cancel();
                    flingAnim = ValueAnimator.ofFloat(xVelocity / 100 * L.dp_1, 0);
                    flingAnim.setDuration(Math.abs((int) xVelocity) / 2);
                    flingAnim.addUpdateListener(animation -> {
                        float value = (float) animation.getAnimatedValue();
                        executeScrollXBy(-value);
                    });
                    flingAnim.start();
                    LogUtil.d("==============fling===================xVelocity:" + xVelocity);
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


    private List<ViewItem> drawList = new ArrayList<>();

    private void test(Canvas canvas) {
        float itemWidth = L.dp_1 * 40;
        float itemHeight = L.dp_1 * 20;
        int totalSize = testData.size();
        maxScrollWidth = itemWidth * totalSize - getMeasuredWidth();
        float scrolledX = mScroller.getFinalX(); //已经滚过的距离

        //  LogUtil.d("===========scrolledX:" + scrolledX);
        //每次最多只绘制1屏
        int sizeOfOneDraw = (int) (getMeasuredWidth() / itemWidth + 1);
        if (drawList.size() == 0) {
            for (int i = 0; i < sizeOfOneDraw; i++) {
                drawList.add(testData.get(i));
            }
        } else {
            drawList.clear();
            float scrolledItemSize = scrolledX / itemWidth; //已经滚过的Item数目
            int start = (int) Math.floor(scrolledItemSize);
            for (int i = start; i < start + sizeOfOneDraw; i++) {
                if (i < totalSize) {
                    ViewItem viewItem = testData.get(i);
                    viewItem.offset = -(scrolledItemSize - start) * itemWidth;
                    drawList.add(viewItem);
                }

            }
        }

        int drawListSize = drawList.size();
        for (int i = 0; i < drawListSize; i++) {
            ViewItem viewItem = drawList.get(i);
            viewItem.start = viewItem.offset + i * itemWidth;
            viewItem.end = viewItem.start + itemWidth;
            viewItem.top = L.dp_1 * 20;
            viewItem.bottom = viewItem.top + itemHeight;
            paint.setColor(viewItem.index % 2 == 0 ? Color.RED : Color.GREEN);
            canvas.drawRect(viewItem.start, viewItem.top, viewItem.end, viewItem.bottom, paint);
            canvas.drawText(viewItem.data.toString(), viewItem.start + itemWidth / 2f, viewItem.top, paint);
        }
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //  scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
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