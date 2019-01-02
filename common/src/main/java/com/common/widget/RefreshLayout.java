package com.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.common.R;
import com.common.utils.DateUtil;
import com.common.utils.LogUtil;
import com.common.utils.SharedPreUtils;
import com.common.utils.ViewUtil;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;
import com.scwang.smartrefresh.layout.internal.ProgressDrawable;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class RefreshLayout extends LinearLayout {

    private float min_scroll_unit;
    private ArrowDrawable arrowDrawableBottom;
    private ProgressDrawable progressDrawableBottom;
    private View headerView;
    private int headerOrFooterHeight;
    private int headerRefreshHeight;
    private int footerLoadHeight;
    private View targetView;
    private TextView tv_header_state;
    private Context context;
    private static final String text_pull_down_refresh = "下拉刷新";
    private static final String text_release_refresh = "释放刷新";
    private static final String text_refreshing = "正在刷新...";
    private static final String text_refresh_finish = "刷新完成";
    private static final String key_refresh_last_update = "key_refresh_last_update";
    private static final String last_update_time_no_record = "上次更新 ...";
    private static final String last_update_time_prefix = "上次更新";

    private static final String text_pull_up_load = "上拉加载更多";
    private static final String text_release_load = "释放立即加载";
    private static final String text_loading = "正在加载中...";
    private static final String text_load_finish = "加载更多完成";

    private TextView tv_header_date;
    private ImageView iv_header_right;
    private ProgressDrawable progressDrawableTop;
    private ArrowDrawable arrowDrawableTop;
    private TextView tv_footer_state;
    private ImageView iv_footer_right;

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
        footerLoadHeight = Math.round(getResources().getDimension(R.dimen.dp_60));
        headerOrFooterHeight = Math.round(getResources().getDimension(R.dimen.dp_150));
        progressDrawableTop = new ProgressDrawable();
        progressDrawableTop.setColor(0xff666666);
        arrowDrawableTop = new ArrowDrawable();
        arrowDrawableTop.setColor(0xff666666);

        arrowDrawableBottom = new ArrowDrawable();
        arrowDrawableBottom.setColor(0xff666666);

        progressDrawableBottom = new ProgressDrawable();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = getChildAt(0);
        View footerView = getChildAt(2);
        tv_header_state = headerView.findViewById(R.id.tv_header_state);
        tv_header_date = headerView.findViewById(R.id.tv_header_date);
        iv_header_right = headerView.findViewById(R.id.iv_header_right);
        iv_header_right.setImageDrawable(arrowDrawableTop);

        tv_footer_state = footerView.findViewById(R.id.tv_footer_state);
        iv_footer_right = footerView.findViewById(R.id.iv_footer_right);
        iv_footer_right.setImageDrawable(arrowDrawableBottom);
        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
        lp.topMargin = -headerOrFooterHeight;
        lp.height = headerOrFooterHeight;
        headerView.setLayoutParams(lp);

        LayoutParams lp_footer = (LayoutParams) footerView.getLayoutParams();
        lp_footer.height = headerOrFooterHeight;
        footerView.setLayoutParams(lp_footer);
        LogUtil.d(" =====onFinishInflate headerView:" + headerView.getMeasuredHeight());
    }

    private void touchScroll(int dy) {
        if (dy != 0) {
            if (headerOrFooterAnim != null && headerOrFooterAnim.isRunning()) {
                headerOrFooterAnim.end();
                headerOrFooterAnim.cancel();
            }
        }
        scrollBy(0, dy);
    }

    private int refresh_state = 1;
    private final int refresh_state_pull_down = 1;
    private final int refresh_state_release_refresh = 2;
    private final int refresh_state_refreshing = 3;
    private final int refresh_state_refresh_finished = 4;

    private int load_state = 11;
    private final int load_state_up_load = 11;
    private final int load_state_release_load = 12;
    private final int load_state_loading = 13;
    private final int load_state_finished = 14;

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
                            updateHeaderAndFooterState();
                            if (dy > 0) { //向下滑
                                boolean canScrollDown = targetView.canScrollVertically(-1);
                                if (!canScrollDown) { //子视图不能下滑或者 footer出来了
                                    scrollShowHeaderOrFooter(dy);
                                }
                                if (getScrollY() > 0) {//隐藏footer
                                    touchScroll(-Math.round(dy));
                                }
                            } else if (dy < 0) {//向上滑
                                boolean canUpScroll = targetView.canScrollVertically(1);
                                if (getScrollY() < 0) {  //如果头已经出来 隐藏头
                                    touchScroll(-Math.round(dy));
                                } else { //上拉加载更多
                                    if (!canUpScroll) {
                                        scrollShowHeaderOrFooter(dy);
                                    }
                                }
                                //   LogUtil.d("向上滑动:" + b1 + "  dy:" + dy);
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
                            scrollHeaderOrFooterView(getScrollY(), -headerRefreshHeight, refresh_state_refreshing, true);//滚动到刷新位置
                            break;
                        case refresh_state_pull_down:  //下拉刷新
                            scrollHeaderOrFooterView(getScrollY(), 0, refresh_state_pull_down, true);//滚动到关闭位置
                            break;
                        case refresh_state_refreshing:  //正在刷新
                            if (getScrollY() <= -headerRefreshHeight) {//正在在释放刷新位置
                                scrollHeaderOrFooterView(getScrollY(), -headerRefreshHeight, refresh_state_refreshing, true);//滚动到刷新位置
                            } else { // 正在下拉刷新位置
                                scrollHeaderOrFooterView(getScrollY(), 0, refresh_state_refreshing, true);//滚动到关闭位置
                            }
                            break;
                    }
                }

                if (getScrollY() > 0) {//脚部显示出来
                    switch (load_state) {
                        case load_state_release_load: //释放加载更多
                            scrollHeaderOrFooterView(getScrollY(), footerLoadHeight, load_state_loading, false);//滚动到刷新位置
                            break;
                        case load_state_up_load:  //上拉加载更多
                            scrollHeaderOrFooterView(getScrollY(), 0, load_state_up_load, false);//滚动到关闭位置
                            break;
                        case load_state_loading:  //正在加载更多
                            if (getScrollY() >= footerLoadHeight) {//正在在释放加载更多位置
                                scrollHeaderOrFooterView(getScrollY(), footerLoadHeight, load_state_loading, false);//滚动到刷新位置
                            } else { // 正在上拉加载更多位置
                                scrollHeaderOrFooterView(getScrollY(), 0, load_state_loading, false);//滚动到关闭位置
                            }
                            break;
                    }
                }
                break;
        }
        return consume;
    }

    private void scrollShowHeaderOrFooter(float dy) {
        float damping = Math.abs(getScrollY()) / (float) headerOrFooterHeight;//damping_level_1 值域：[0 - 1] 和下拉距离成正比
        float damping_level_1 = 1 - damping; //[1 - 0] //一级阻尼
        int scroll_dy = -Math.round(dy * damping_level_1);
        touchScroll(scroll_dy);
    }

    private void updateRecordTime() {
        long lastTime = SharedPreUtils.getLong(context, key_refresh_last_update, 0);
        if (lastTime == 0) {
            tv_header_date.setText(last_update_time_no_record);
        } else {
            String dateOfDay = DateUtil.getDateOfDay(lastTime);
            dateOfDay = "今天".equals(dateOfDay) ? " " : dateOfDay;
            String timeOfDay = DateUtil.getTimeOfDay(lastTime);
            String hourAndMinute = DateUtil.getHourAndMinute(lastTime);
            String showText = last_update_time_prefix + dateOfDay + " " + timeOfDay + " " + hourAndMinute;
            tv_header_date.setText(showText);
        }
    }

    private void updateHeaderAndFooterState() {
        float angle = Math.abs(getScrollY()) / (float) headerRefreshHeight;
        angle = angle > 1 ? 1 : angle;
        iv_header_right.setRotation(angle * 180);
        iv_footer_right.setRotation(angle * 180 + 180);

        if (refresh_state == refresh_state_release_refresh || refresh_state == refresh_state_pull_down) {
            if (getScrollY() <= -headerRefreshHeight) {//释放刷新
                refresh_state = refresh_state_release_refresh;
                tv_header_state.setText(text_release_refresh);
            } else if (getScrollY() < 0) { //关闭头
                refresh_state = refresh_state_pull_down;
                tv_header_state.setText(text_pull_down_refresh);
            }
        }

        if (load_state == load_state_release_load || load_state == load_state_up_load) {
            if (getScrollY() >= footerLoadHeight) {//释放加载更多
                load_state = load_state_release_load;
                tv_footer_state.setText(text_release_load);
                LogUtil.d("" + "  getScrollY:" + getScrollY() + "  footerLoadHeight:" + footerLoadHeight);
            } else if (getScrollY() > 0) { //关闭头
                load_state = load_state_up_load;
                tv_footer_state.setText(text_pull_up_load);
            }
        }
    }

    ValueAnimator headerOrFooterAnim;

    private void scrollHeaderOrFooterView(int start, int end, int end_state, boolean isHeader) {
        headerOrFooterAnim = ValueAnimator.ofInt(start, end);
        float height = isHeader ? headerRefreshHeight : footerLoadHeight;
        float rate = Math.abs(end - start) / height;
        rate = rate > 1 ? 1 : rate;
        rate = rate < 0 ? 0.01f : rate;
        headerOrFooterAnim.setDuration(Math.round(rate * 200));
        headerOrFooterAnim.addUpdateListener(animation -> scrollTo(0, (Integer) animation.getAnimatedValue()));
        headerOrFooterAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (end_state == refresh_state_pull_down) {
                    refresh_state = refresh_state_pull_down;
                    tv_header_state.setText(text_pull_down_refresh);
                    iv_header_right.setImageDrawable(arrowDrawableTop);
                    iv_header_right.setVisibility(View.VISIBLE);
                } else if (end_state == refresh_state_refreshing && refresh_state != refresh_state_refreshing) {
                    refresh_state = refresh_state_refreshing;
                    tv_header_state.setText(text_refreshing);
                    iv_header_right.setImageDrawable(progressDrawableTop);
                    progressDrawableTop.start();
                    if (onRefreshListener != null) onRefreshListener.onRefresh(RefreshLayout.this);
                    SharedPreUtils.putLong(context, key_refresh_last_update, System.currentTimeMillis());//保存现在时间
                } else if (end_state == refresh_state_refresh_finished &&  refresh_state != refresh_state_refresh_finished) {
                    refresh_state = refresh_state_refresh_finished;
                    tv_header_state.setText(text_refresh_finish);
                    iv_header_right.setVisibility(View.INVISIBLE);
                    headerView.postDelayed(() -> scrollHeaderOrFooterView(getScrollY(), 0, refresh_state_pull_down, true), 700);
                }

                if (end_state == load_state_up_load) {
                    load_state = load_state_up_load;
                    tv_footer_state.setText(text_pull_up_load);
                    iv_footer_right.setVisibility(View.VISIBLE);
                    iv_footer_right.setImageDrawable(arrowDrawableBottom);
                } else if (end_state == load_state_loading && load_state != load_state_loading) {
                    load_state = load_state_loading;
                    tv_footer_state.setText(text_loading);
                    iv_footer_right.setImageDrawable(progressDrawableBottom);
                    progressDrawableBottom.start();
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore(RefreshLayout.this);
                    }
                } else if (end_state == load_state_finished && load_state != load_state_finished) {
                    load_state = load_state_finished;
                    tv_footer_state.setText(text_load_finish);
                    iv_footer_right.setVisibility(View.INVISIBLE);
                    tv_footer_state.postDelayed(() -> scrollHeaderOrFooterView(getScrollY(), 0, load_state_up_load, false), 700);
                }
            }
        });
        headerOrFooterAnim.start();
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

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        progressDrawableTop.stop();
        progressDrawableBottom.stop();
        if (headerOrFooterAnim != null) {
            headerOrFooterAnim.removeAllListeners();
            headerOrFooterAnim.removeAllUpdateListeners();
            headerOrFooterAnim.cancel();
        }
    }

    private OnRefreshListener onRefreshListener;

    private OnLoadMoreListener onLoadMoreListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void notifyLoadMoreFinish() {
        scrollHeaderOrFooterView(getScrollY(), getScrollY(), load_state_finished, false);
    }

    public void notifyRefreshFinish() {
        scrollHeaderOrFooterView(getScrollY(), getScrollY(), refresh_state_refresh_finished, false);
    }

    public interface OnRefreshListener {
        void onRefresh(RefreshLayout refreshLayout);
    }

    public interface OnLoadMoreListener {
        void onLoadMore(RefreshLayout refreshLayout);
    }
}