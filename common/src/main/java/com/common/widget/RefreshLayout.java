package com.common.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.common.utils.LogUtil;
import com.common.utils.ViewUtil;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class RefreshLayout extends LinearLayout {

    private final Scroller mScroller;
    private View headerView;
    private int headerHeight;
    private View targetView;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mScroller.forceFinished(true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = getChildAt(0);
        LogUtil.d(" =====onFinishInflate headerView:" + headerView.getMeasuredHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headerHeight = headerView.getMeasuredHeight();
    }

/*    public interface onRefreshListener {
        void onRefresh(RefreshLayout refreshLayout);
    }

    public interface onLoadMoreListener {
        void onLoadMore(RefreshLayout refreshLayout);
    }*/

    public void scroll(int dy, int real_dy, RecyclerView rv) {
        LogUtil.d("========scroll====dy:" + dy + "  real_dy:" + real_dy);
        if (real_dy == 0) { //RV没有滚动
            executeScrollYBy(dy);
        }
        if (dy > 0 && getScrollY() > -headerView.getHeight()) { //向上滑,头部已经显示出来
            executeScrollYBy(dy);
        }
    }

    private void executeScrollYBy(int dy) {
        if (getScrollY() + dy < -headerHeight) {
            dy = -headerHeight - getScrollY();
        }
        if (getScrollY() + dy > 0) dy = -getScrollY();
        scrollBy(0, dy);
    }

    private float startX;
    private float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean b = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                targetView = findTargetView(startX, startY);
                if (targetView == null) LogUtil.e("下拉刷新控件，没有发现目标ViewGroup");
                break;
            case MotionEvent.ACTION_MOVE:
                if (targetView != null) {
                    float currentX = ev.getRawX();
                    float currentY = ev.getRawY();
                    float dx = currentX - startX;
                    float dy = currentY - startY;
                    startX = currentX;
                    startY = currentY;
                    if (Math.abs(dy) > Math.abs(dx)) {

                        if (dy > 0) { //向下滑
                            boolean b1 = targetView.canScrollVertically(-1);
                            LogUtil.d("================目标控件 是否能 向下滑动b1:" + dy);
                        } else if (dy < 0) {//向上滑
                            boolean b1 = targetView.canScrollVertically(1);
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (getScrollY() >= -headerView.getHeight()) { //向上滑,头部已经显示出来
                    closeHeaderView(getScrollY(), 0);
                }
                break;
        }
        return b;
    }

    private void closeHeaderView(int start, int end) {
        ValueAnimator anim = ValueAnimator.ofInt(start, end);
        anim.setDuration(200);
        anim.addUpdateListener(animation -> {
            scrollTo(0, (Integer) animation.getAnimatedValue());
        });
        anim.start();
    }

    private View[] targetViewArr;

    public void setTargetView(View... targetView) {
        targetViewArr = targetView;
    }

    private View findTargetView(float x, float y) {
        if (targetViewArr == null || targetViewArr.length == 0) {
            return null;
        } else {
            for (View aTargetViewArr : targetViewArr) {
                if (ViewUtil.isTouchPointInView(aTargetViewArr, x, y)) {
                    return aTargetViewArr;
                }
            }
        }
        return null;
    }
}