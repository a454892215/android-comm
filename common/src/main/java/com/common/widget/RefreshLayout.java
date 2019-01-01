package com.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.common.R;
import com.common.utils.LogUtil;
import com.common.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class RefreshLayout extends LinearLayout {

    private View headerView;
    private int headerHeight;
    private static int headerRefreshHeight;
    private View targetView;
    private TextView tv_header;

    private static final String text_pull_down_refresh = "下拉刷新";
    private static final String text_release_refresh = "释放刷新";
    private static final String text_refreshing = "正在刷新";

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Scroller mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        headerRefreshHeight = Math.round(getResources().getDimension(R.dimen.dp_60));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = getChildAt(0);
        tv_header = headerView.findViewById(R.id.tv_header);
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


    private void touchScroll(int dy) {
        if (getScrollY() + dy < -headerHeight) {
            dy = -headerHeight - getScrollY();
        }
        if (getScrollY() + dy > 0) dy = -getScrollY();

        if (dy != 0) {
            removeAllHeaderRefreshRunnable();
        }
        scrollBy(0, dy);
    }

    private float startX;
    private float startY;
    private float sumYPerTouch;
    private static final float damping = 0.8f;//阻尼，越小 头部越容易下拉

    private static int refresh_state = 1;
    private static final int refresh_state_pull_down = 1;
    private static final int refresh_state_release_refresh = 2;
    private static final int refresh_state_refreshing = 3;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean consume = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("===============ACTION_DOWN==============");
                startX = ev.getRawX();
                startY = ev.getRawY();
                sumYPerTouch = 0;
                targetView = findTargetView(startX, startY);
                if (targetView == null) LogUtil.e("下拉刷新控件，没有发现目标ViewGroup");
                break;
            case MotionEvent.ACTION_MOVE:
                if (targetView != null) {
                    float currentX = ev.getRawX();
                    float currentY = ev.getRawY();
                    float dx = currentX - startX;
                    float dy = currentY - startY;
                    sumYPerTouch += dy * damping;
                    sumYPerTouch = sumYPerTouch > headerHeight ? headerHeight : sumYPerTouch;
                    startX = currentX;
                    startY = currentY;
                    //    LogUtil.d("getScrollY():" + getScrollY() + "  headerHeight:" + headerHeight + " headerRefreshHeight:" + headerRefreshHeight);
                    if (Math.abs(dy) > Math.abs(dx)) {
                        if (dy > 0) { //向下滑
                            boolean canScrollDown = targetView.canScrollVertically(-1);
                            if (!canScrollDown) {
                                if (getScrollY() < 0 && refresh_state != refresh_state_refreshing) { //头部显示出来
                                    if (getScrollY() <= -headerRefreshHeight) {//释放刷新
                                        refresh_state = refresh_state_release_refresh;
                                        tv_header.setText(text_release_refresh);
                                    } else { //关闭头
                                        refresh_state = refresh_state_pull_down;
                                        tv_header.setText(text_pull_down_refresh);
                                    }
                                    if (headerAnim != null && headerAnim.isRunning()) {
                                        headerAnim.end();
                                        headerAnim.cancel();
                                    }
                                }
                                float rate = sumYPerTouch / headerHeight;//0 - 1
                                int scroll_dy = -Math.round(dy * (1 - rate));
                                scroll_dy = scroll_dy == 0 ? -1 : scroll_dy;
                                touchScroll(scroll_dy);
                            }
                        } else if (dy < 0) {//向上滑

                            if (getScrollY() < 0) {  //如果头已经出来 隐藏头
                                touchScroll(-Math.round(dy));
                                if (headerAnim != null && headerAnim.isRunning()) {
                                    headerAnim.end();
                                    headerAnim.cancel();
                                }
                            }
                            boolean b1 = targetView.canScrollVertically(1);
                            LogUtil.d("向上滑动:" + b1 + "  dy:" + dy);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.d("===============ACTION_UP==============");
                if (getScrollY() < 0) { //头部显示出来
                    switch (refresh_state) {
                        case refresh_state_release_refresh: //释放刷新
                            scrollHeaderView(getScrollY(), -headerRefreshHeight, refresh_state_refreshing);//滚动到刷新位置
                            break;
                        case refresh_state_pull_down:  //下拉刷新
                            scrollHeaderView(getScrollY(), 0, refresh_state_pull_down);//滚动到关闭位置
                            break;
                        case refresh_state_refreshing:  //正在刷新
                            if (getScrollY() <= -headerRefreshHeight) {//正在在释放刷新位置
                                scrollHeaderView(getScrollY(), -headerRefreshHeight, refresh_state_refreshing);//滚动到刷新位置
                            } else { // 正在下拉刷新位置
                                scrollHeaderView(getScrollY(), 0, refresh_state_refreshing);//滚动到关闭位置
                            }
                            break;
                    }
                }
                break;
        }
        return consume;
    }

    ValueAnimator headerAnim;
    List<Runnable> endHeaderRefreshList = new ArrayList<>();

    private void scrollHeaderView(int start, int end, int end_state) {
        headerAnim = ValueAnimator.ofInt(start, end);
        headerAnim.setDuration(250);
        headerAnim.addUpdateListener(animation -> scrollTo(0, (Integer) animation.getAnimatedValue()));
        headerAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (end_state == refresh_state_pull_down) {
                    refresh_state = refresh_state_pull_down;
                    tv_header.setText(text_pull_down_refresh);
                    removeAllHeaderRefreshRunnable();
                } else if (end_state == refresh_state_refreshing) {
                    refresh_state = refresh_state_refreshing;
                    tv_header.setText(text_refreshing);
                    Runnable runnable = () -> scrollHeaderView(getScrollY(), 0, refresh_state_pull_down);
                    endHeaderRefreshList.add(runnable);
                    headerView.postDelayed(runnable, 3000);
                }
            }
        });
        headerAnim.start();
    }

    private void removeAllHeaderRefreshRunnable() {
        for (Runnable runnable : endHeaderRefreshList) {
            if (runnable != null) headerView.removeCallbacks(runnable);
        }
        endHeaderRefreshList.clear();
    }

    private View[] targetViewArr;

    public void setTargetView(View... targetView) {
        targetViewArr = targetView;
    }

    private View findTargetView(float x, float y) {
        if (targetViewArr == null || targetViewArr.length == 0) {
            LogUtil.e("下拉刷新控件，没有设置目标ViewGroup");
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