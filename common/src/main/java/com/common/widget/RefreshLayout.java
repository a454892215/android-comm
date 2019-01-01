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
import com.common.utils.DateUtil;
import com.common.utils.LogUtil;
import com.common.utils.SharedPreUtils;
import com.common.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class RefreshLayout extends LinearLayout {

    private final float min_scroll_unit;
    private View headerView;
    private int headerHeight;
    private static int headerRefreshHeight;
    private View targetView;
    private TextView tv_header;
    private Context context;
    private static final String text_pull_down_refresh = "下拉刷新";
    private static final String text_release_refresh = "释放刷新";
    private static final String text_refreshing = "正在刷新";
    private static final String key_refresh_last_update = "key_refresh_last_update";
    private static final String last_update_time_no_record = "上次更新 ...";
    private static final String last_update_time_prefix = "上次更新";

    private TextView tv_header_date;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        min_scroll_unit = context.getResources().getDimension(R.dimen.dp_1);
        Scroller mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        headerRefreshHeight = Math.round(getResources().getDimension(R.dimen.dp_60));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = getChildAt(0);
        tv_header = headerView.findViewById(R.id.tv_header_state);
        tv_header_date = headerView.findViewById(R.id.tv_header_date);
        LogUtil.d(" =====onFinishInflate headerView:" + headerView.getMeasuredHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        headerHeight = headerView.getMeasuredHeight();
        if (headerHeight != 0) {
            LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
            lp.topMargin = -headerHeight;
            headerView.setLayoutParams(lp);
        }

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
            if (headerAnim != null && headerAnim.isRunning()) {
                headerAnim.end();
                headerAnim.cancel();
            }
        }
        scrollBy(0, dy);
    }

    private static int refresh_state = 1;
    private static final int refresh_state_pull_down = 1;
    private static final int refresh_state_release_refresh = 2;
    private static final int refresh_state_refreshing = 3;

    private float startX;
    private float startY;

    private static final int max_compute_times = 3;
    private int compute_times = 0;
    private float xScrollSum;
    private float yScrollSum;

    private int orientation = 0;
    private static final int orientation_vertical = 1;
    private static final int orientation_horizontal = 2;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean consume = super.dispatchTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.d("===============ACTION_DOWN==============");
                startX = ev.getRawX();
                startY = ev.getRawY();
                orientation = 0;
                xScrollSum = 0;
                yScrollSum = 0;
                compute_times = 0;
                targetView = findTargetView(startX, startY);
                if (targetView == null) LogUtil.e("下拉刷新控件，没有发现目标ViewGroup");
                updateRecordTime();

                break;
            case MotionEvent.ACTION_MOVE:
                if (targetView != null) {
                    float currentX = ev.getRawX();
                    float currentY = ev.getRawY();
                    float dx = currentX - startX;
                    float dy = currentY - startY;

                    if (compute_times < max_compute_times || (Math.abs(xScrollSum) < min_scroll_unit &&
                            Math.abs(yScrollSum) < min_scroll_unit)) {//第一个条件限制最大次数 ，避免首几次数据有误
                        xScrollSum += dx;
                        yScrollSum += dy;
                        compute_times++;
                    }
                    startX = currentX;
                    startY = currentY;

                    if (Math.abs(xScrollSum) > min_scroll_unit && Math.abs(xScrollSum) > Math.abs(yScrollSum)) {
                        if (orientation == 0) orientation = orientation_horizontal;
                    } else if (Math.abs(yScrollSum) > min_scroll_unit && Math.abs(yScrollSum) > Math.abs(xScrollSum)) {
                        if (orientation == 0) orientation = orientation_vertical;
                        if (orientation == orientation_vertical) {
                            updateHeaderState();
                            if (dy > 0) { //向下滑
                                boolean canScrollDown = targetView.canScrollVertically(-1);
                                if (!canScrollDown) {
                                    float damping = -getScrollY() / (float) headerHeight;//damping_level_1 值域：[0 - 1] 和下拉距离成正比
                                    float damping_level_1 = 1 - damping; //[1 - 0] //一级阻尼
                                    int scroll_dy = -Math.round(dy * damping_level_1);
                                    touchScroll(scroll_dy);
                                }
                            } else if (dy < 0) {//向上滑
                                if (getScrollY() < 0) {  //如果头已经出来 隐藏头
                                    touchScroll(-Math.round(dy));
                                }
                                boolean b1 = targetView.canScrollVertically(1);
                                LogUtil.d("向上滑动:" + b1 + "  dy:" + dy);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
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

    private void updateRecordTime() {
        long lastTime = SharedPreUtils.getLong(context, key_refresh_last_update, 0);
        if (lastTime == 0) {
            tv_header_date.setText(last_update_time_no_record);
        } else {
            String dateOfDay = DateUtil.getDateOfDay(lastTime);
            dateOfDay = "今天".equals(dateOfDay) ? "" : dateOfDay;
            String timeOfDay = DateUtil.getTimeOfDay(lastTime);
            String hourAndMinute = DateUtil.getHourAndMinute(lastTime);
            String showText = last_update_time_prefix + dateOfDay + " " + timeOfDay + " " + hourAndMinute;
            tv_header_date.setText(showText);
        }
    }

    private void updateHeaderState() {
        if (refresh_state != refresh_state_refreshing) {
            if (getScrollY() <= -headerRefreshHeight) {//释放刷新
                refresh_state = refresh_state_release_refresh;
                tv_header.setText(text_release_refresh);
            } else { //关闭头
                refresh_state = refresh_state_pull_down;
                tv_header.setText(text_pull_down_refresh);
            }
        }
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

                    long currentTimeMillis = System.currentTimeMillis();
                    SharedPreUtils.putLong(context, key_refresh_last_update, currentTimeMillis);//保存现在时间
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