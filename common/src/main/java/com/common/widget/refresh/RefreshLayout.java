package com.common.widget.refresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
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
 * Author: L
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
    private String key_refresh_last_update = "key_refresh_last_update_";
    private static final String last_update_time_no_record = "上次更新   很久以前  ...";
    private static final String last_update_time_prefix = "上次更新";

    private static final String text_pull_up_load = "上拉加载更多";
    private static final String text_release_load = "释放立即加载";
    private static final String text_loading = "正在加载中...";
    private static final String text_load_finish = "加载更多完成";

    private static final float damping_level_2 = 0.2f; //二级阻尼 值越小 滑动阻力越小 取值[0-1]

    private TextView tv_header_date;
    private ImageView iv_header_right;
    private ProgressDrawable progressDrawableTop;
    private ArrowDrawable arrowDrawableTop;
    private TextView tv_footer_state;
    private ImageView iv_footer_right;
    private float header_text_size;
    private float footer_text_size;
    private String default_header_function;
    private String default_footer_function;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
        this.context = context;
        min_scroll_unit = context.getResources().getDimension(R.dimen.dp_1);
        Scroller mScroller = new Scroller(context);
        mScroller.forceFinished(true);
        headerRefreshHeight = Math.round(getResources().getDimension(R.dimen.dp_60));
        footerLoadHeight = Math.round(getResources().getDimension(R.dimen.dp_60));
        headerOrFooterHeight = Math.round(getResources().getDimension(R.dimen.dp_150));

        int color = 0xff666666;
        progressDrawableTop = new ProgressDrawable();
        progressDrawableTop.setColor(color);
        arrowDrawableTop = new ArrowDrawable();
        arrowDrawableTop.setColor(color);

        arrowDrawableBottom = new ArrowDrawable();
        arrowDrawableBottom.setColor(color);
        progressDrawableBottom = new ProgressDrawable();
        progressDrawableBottom.setColor(color);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, defStyleAttr, 0);
        default_header_function = typedArray.getString(R.styleable.RefreshLayout_header_function);
        default_footer_function = typedArray.getString(R.styleable.RefreshLayout_footer_function);
        float dp_13 = context.getResources().getDimension(R.dimen.dp_13);
        header_text_size = typedArray.getDimension(R.styleable.RefreshLayout_header_text_size, Math.round(dp_13));
        footer_text_size = typedArray.getDimension(R.styleable.RefreshLayout_footer_text_size, Math.round(dp_13));
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = LayoutInflater.from(context).inflate(R.layout.group_refresh_header, this, false);
        View footerView = LayoutInflater.from(context).inflate(R.layout.group_refresh_footer, this, false);
        this.addView(headerView, 0);
        this.addView(footerView);

        tv_header_state = headerView.findViewById(R.id.tv_header_state);
        tv_header_date = headerView.findViewById(R.id.tv_header_date);
        iv_header_right = headerView.findViewById(R.id.iv_header_right);
        iv_header_right.setImageDrawable(arrowDrawableTop);
        tv_header_state.setTextSize(TypedValue.COMPLEX_UNIT_PX, header_text_size);
        tv_header_date.setTextSize(TypedValue.COMPLEX_UNIT_PX, header_text_size * 0.88f);

        tv_footer_state = footerView.findViewById(R.id.tv_footer_state);
        iv_footer_right = footerView.findViewById(R.id.iv_footer_right);
        iv_footer_right.setImageDrawable(arrowDrawableBottom);
        tv_footer_state.setTextSize(TypedValue.COMPLEX_UNIT_PX, footer_text_size);

        LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
        lp.topMargin = -headerOrFooterHeight;
        lp.height = headerOrFooterHeight;
        headerView.setLayoutParams(lp);

        LayoutParams lp_footer = (LayoutParams) footerView.getLayoutParams();
        lp_footer.height = headerOrFooterHeight;
        footerView.setLayoutParams(lp_footer);

        if ("refresh".equals(default_header_function)) {
            setHeaderFunction(HeaderFunction.refresh);
        } else if ("forbid_scroll".equals(default_header_function)) {
            setHeaderFunction(HeaderFunction.forbid_scroll);
        } else {
            setHeaderFunction(HeaderFunction.only_display);
        }

        if ("load_more".equals(default_footer_function)) {
            setFooterFunction(FooterFunction.load_more);
        } else if ("forbid_scroll".equals(default_footer_function)) {
            setFooterFunction(FooterFunction.forbid_scroll);
        } else if ("only_display".equals(default_footer_function)) {
            setFooterFunction(FooterFunction.only_display);
        }
    }

    private static final int no_handle_state = -1; //动画完成后 不需要处理的状态

    private int refresh_state = 1;
    static final int refresh_state_pull_down = 1;
    static final int refresh_state_release = 2;
    static final int refresh_state_refreshing = 3;
    static final int refresh_state_finished = 4;
    static final int refresh_state_mutex = 5; // 互斥状态 当正在加载更多状态的时候
    static final int refresh_state_forbid_scroll = 6; //禁止状态
    static final int refresh_state_only_display = 7; //只展示文本状态

    private int load_state = 11;
    static final int load_state_up_load = 11;
    static final int load_state_release_load = 12;
    static final int load_state_loading = 13;
    static final int load_state_finished = 14;
    static final int load_state_mutex = 15;
    static final int load_state_forbid_scroll = 16; //禁止状态
    static final int load_state_only_display = 17; //只展示文本状态

    private float startX;
    private float startY;

    private static final int max_compute_times = 3;
    private int compute_times = 0;
    private float xScrollSum;
    private float yScrollSum;

    private int orientation = 0;
    private static final int orientation_vertical = 1;
    private static final int orientation_horizontal = 2;

    boolean isIntercept = false;

    private int pointerId = -1;
    private boolean pointerIdIsChange = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if ((load_state == load_state_finished || refresh_state == refresh_state_finished)) {
            return true;
        }
        int actionIndex = ev.getActionIndex();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getRawX();
                startY = ev.getRawY();
                orientation = 0;
                xScrollSum = 0;
                yScrollSum = 0;
                compute_times = 0;
                isIntercept = false;
                targetView = findTargetView(startX, startY);
                if (targetView == null) LogUtil.e("下拉刷新控件，没有发现目标ViewGroup");
                pointerIdIsChange = false;
                pointerId = ev.getPointerId(actionIndex);
                updateRecordTime();
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerId_move = ev.getPointerId(actionIndex);
                if (pointerId_move != pointerId) pointerIdIsChange = true;
                if (pointerIdIsChange) break;
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
                            onConfirmVerticalTouch(dy);
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isIntercept = false;
                onUpAnimUpdatePosition();
                break;
        }
        boolean consume = true;
        if (!isIntercept) consume = super.dispatchTouchEvent(ev);
        return consume;
    }

    private void onConfirmVerticalTouch(float dy) {
        if (dy > 0) { //向下滑
            if (refresh_state == refresh_state_forbid_scroll) return;
            boolean canScrollDown = targetView.canScrollVertically(-1);
            if (!canScrollDown) { //拉出header
                touchScrollWithDamping(-dy);
            }
            if (getScrollY() > 0) {// footer出来了 隐藏
                touchScroll(Math.round(-dy));
                isIntercept = true;
            } else {
                isIntercept = false;
            }
            onMoveUpdateState();
        } else if (dy < 0) {//向上滑
            if (load_state == load_state_forbid_scroll) return;
            boolean canUpScroll = targetView.canScrollVertically(1);
            if (!canUpScroll) {//拉出footer
                touchScrollWithDamping(-dy);
            }

            if (getScrollY() < 0) {  //如果头已经出来 隐藏头
                touchScroll(Math.round(-dy));
                isIntercept = true;
            } else {
                isIntercept = false;
            }
            onMoveUpdateState();
        }
    }

    private void touchScrollWithDamping(float dy) {
        float damping = Math.abs(getScrollY()) / (float) headerOrFooterHeight;//damping_level_1 值域：[0 - 1] 和下拉距离成正比
        float damping_level_1 = 1 - damping; //[1 - 0] //一级阻尼
        int scroll_dy = Math.round(dy * damping_level_1 * (1 - damping_level_2));
        touchScroll(scroll_dy);
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

    private void onUpAnimUpdatePosition() {
        if (getScrollY() < 0) { //头部显示出来
            switch (refresh_state) {
                case refresh_state_release: //释放刷新
                    animUpdateState(getScrollY(), -headerRefreshHeight, refresh_state_refreshing, true);//滚动到刷新位置
                    break;
                case refresh_state_pull_down:  //下拉刷新
                    animUpdateState(getScrollY(), 0, refresh_state_pull_down, true);//滚动到关闭位置
                    break;
                case refresh_state_refreshing:  //正在刷新
                    if (getScrollY() <= -headerRefreshHeight) {//正在在释放刷新位置
                        animUpdateState(getScrollY(), -headerRefreshHeight, refresh_state_refreshing, true);//滚动到刷新位置
                    } else { // 正在下拉刷新位置
                        animUpdateState(getScrollY(), 0, refresh_state_refreshing, true);//滚动到关闭位置
                    }
                    break;
                case refresh_state_mutex:
                case refresh_state_only_display:
                    animUpdateState(getScrollY(), 0, no_handle_state, true);
                    break;
            }
        }

        if (getScrollY() > 0) {//脚部显示出来
            switch (load_state) {
                case load_state_release_load: //释放加载更多
                    animUpdateState(getScrollY(), footerLoadHeight, load_state_loading, false);//滚动到刷新位置
                    break;
                case load_state_up_load:  //上拉加载更多
                    animUpdateState(getScrollY(), 0, load_state_up_load, false);//滚动到关闭位置
                    break;
                case load_state_loading:  //正在加载更多
                    if (getScrollY() >= footerLoadHeight) {//正在在释放加载更多位置
                        animUpdateState(getScrollY(), footerLoadHeight, load_state_loading, false);//滚动到刷新位置
                    } else { // 正在上拉加载更多位置
                        animUpdateState(getScrollY(), 0, load_state_loading, false);//滚动到关闭位置
                    }
                    break;
                case load_state_mutex:
                    animUpdateState(getScrollY(), 0, no_handle_state, false);//滚动到关闭位置
                    break;
                case load_state_only_display:
                    animUpdateState(getScrollY(), 0, no_handle_state, false);//滚动到关闭位置
                    break;
            }
        }
    }

    /**
     * 只处理 释放状态和下拉/上拉状态
     */
    private void onMoveUpdateState() {
        float angle = Math.abs(getScrollY()) / (float) headerRefreshHeight;
        angle = angle > 1 ? 1 : angle;
        iv_header_right.setRotation(angle * 180);
        iv_footer_right.setRotation(angle * 180 + 180);

        if (refresh_state == refresh_state_release || refresh_state == refresh_state_pull_down) {
            if (getScrollY() <= -headerRefreshHeight) {//释放刷新
                refresh_state = refresh_state_release;
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
            } else if (getScrollY() > 0) {
                load_state = load_state_up_load;
                tv_footer_state.setText(text_pull_up_load);
            }
        }
    }

    private ValueAnimator headerOrFooterAnim;

    private void animUpdateState(int start, int end, int end_state, boolean isHeader, int... time) {
        headerOrFooterAnim = ValueAnimator.ofInt(start, end);
        float height = isHeader ? headerRefreshHeight : footerLoadHeight;
        int during;
        if (time.length != 0) {
            during = time[0];
        } else {
            float rate = Math.abs(end - start) / height;
            rate = rate > 1 ? 1 : rate;
            rate = rate < 0.8f ? 0.8f : rate;
            during = Math.round(250 * rate);
        }
        headerOrFooterAnim.setDuration(during);
        headerOrFooterAnim.addUpdateListener(animation -> scrollTo(0, (Integer) animation.getAnimatedValue()));
        headerOrFooterAnim.setInterpolator(new DecelerateInterpolator());
        headerOrFooterAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                handleHeaderAnimEndState(end_state);
                handleFooterAnimEndState(end_state);
            }
        });
        headerOrFooterAnim.start();
    }

    private void handleFooterAnimEndState(int end_state) {
        int lastFooterState = load_state;
        if (end_state == load_state_up_load) {
            if (lastFooterState == load_state_finished && targetView != null && isAutoUpScroll) {//如果来自完成状态
                targetView.scrollBy(0, footerLoadHeight);
            }
            load_state = load_state_up_load;
            tv_footer_state.setText(text_pull_up_load);
            iv_footer_right.setVisibility(View.VISIBLE);
            iv_footer_right.setImageDrawable(arrowDrawableBottom);
            if (lastFooterState == load_state_finished && onLoadMoreFinishedResetListener != null) {
                onLoadMoreFinishedResetListener.onFinishReset(RefreshLayout.this);
                onLoadMoreFinishedResetListener = null;
            }
        } else if (end_state == load_state_loading && lastFooterState != load_state_loading) {
            //设置 头部禁止状态
            if (refresh_state == refresh_state_pull_down) {
                refresh_state = refresh_state_mutex;
                tv_header_state.setText(text_loading);
                iv_header_right.setVisibility(View.INVISIBLE);
                tv_header_date.setVisibility(View.INVISIBLE);
            }
            load_state = load_state_loading;
            tv_footer_state.setText(text_loading);
            iv_footer_right.setImageDrawable(progressDrawableBottom);
            progressDrawableBottom.start();
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore(RefreshLayout.this);
            }
        } else if (end_state == load_state_finished && lastFooterState != load_state_finished) {
            //解除 头部禁止状态
            if (refresh_state == refresh_state_mutex) {
                refresh_state = refresh_state_pull_down;
                tv_header_state.setText(text_pull_down_refresh);
                iv_header_right.setVisibility(View.VISIBLE);
                tv_header_date.setVisibility(View.VISIBLE);
            }
            load_state = load_state_finished;
            tv_footer_state.setText(text_load_finish);
            iv_footer_right.setVisibility(View.INVISIBLE);
            tv_footer_state.postDelayed(() -> animUpdateState(getScrollY(), 0, load_state_up_load, false, isAutoUpScroll ? 0 : 200), 500);
        }
    }

    private void handleHeaderAnimEndState(int end_state) {
        int lastHeaderState = refresh_state;
        if (end_state == refresh_state_pull_down) {
            refresh_state = refresh_state_pull_down;
            tv_header_state.setText(text_pull_down_refresh);
            iv_header_right.setImageDrawable(arrowDrawableTop);
            iv_header_right.setVisibility(View.VISIBLE);
            if (lastHeaderState == refresh_state_finished && onRefreshFinishedResetListener != null) {
                onRefreshFinishedResetListener.onFinishReset(RefreshLayout.this);
                onRefreshFinishedResetListener = null;
            }
        } else if (end_state == refresh_state_refreshing && lastHeaderState != refresh_state_refreshing) {
            //设置 脚部禁止状态
            if (load_state == load_state_up_load) {//如果处于待发状态
                load_state = load_state_mutex;
                tv_footer_state.setText(text_refreshing);
                iv_footer_right.setVisibility(View.INVISIBLE);
            }
            refresh_state = refresh_state_refreshing;
            tv_header_state.setText(text_refreshing);
            iv_header_right.setImageDrawable(progressDrawableTop);
            progressDrawableTop.start();
            if (onRefreshListener != null) onRefreshListener.onRefresh(RefreshLayout.this);
            SharedPreUtils.putLong(context, key_refresh_last_update, System.currentTimeMillis());//保存现在时间
        } else if (end_state == refresh_state_finished && lastHeaderState != refresh_state_finished) {
            //解除 脚部禁止状态
            if (load_state == load_state_mutex) {
                load_state = load_state_up_load;
                tv_footer_state.setText(text_pull_up_load);
                iv_footer_right.setVisibility(View.VISIBLE);
            }
            refresh_state = refresh_state_finished;
            tv_header_state.setText(text_refresh_finish);
            iv_header_right.setVisibility(View.INVISIBLE);
            headerView.postDelayed(() -> animUpdateState(getScrollY(), 0, refresh_state_pull_down, true), 500);
        }
    }

    private View[] targetViewArr;

    @SuppressWarnings("unused")
    public void setTargetView(View... targetView) {
        targetViewArr = targetView;
    }

    private View findTargetView(float x, float y) {
        if (targetViewArr == null || targetViewArr.length == 0) {
            if (getChildCount() == 3) return getChildAt(1);
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

    private String headerTextOnOnlyDisplay = "";

    @SuppressWarnings("unused")
    public void setHeaderTextOnOnlyDisplay(String text) {
        headerTextOnOnlyDisplay = text;
        tv_header_state.setText(text);
    }

    private String footerTextOnOnlyDisplay = "";

    @SuppressWarnings("unused")
    public void setFooterTextOnOnlyDisplay(String text) {
        footerTextOnOnlyDisplay = text;
        tv_footer_state.setText(footerTextOnOnlyDisplay);
    }

    private boolean isAutoUpScroll = false;

    /**
     * 只支持RecyclerView
     */
    @SuppressWarnings("unused")
    public void setAutoUpScrollEnableOnLoadMoreFinish(boolean enable) {
        isAutoUpScroll = enable;
    }

    public void setHeaderFunction(HeaderFunction headerFunction) {
        if (refresh_state == refresh_state_pull_down) {
            refresh_state = headerFunction.getHeaderState();
            if (refresh_state == refresh_state_only_display) {
                tv_header_state.setText(headerTextOnOnlyDisplay);
                tv_header_date.setVisibility(View.INVISIBLE);
                iv_header_right.setVisibility(View.INVISIBLE);
            } else if (refresh_state == refresh_state_pull_down) {
                tv_header_date.setVisibility(View.VISIBLE);
                iv_header_right.setVisibility(View.VISIBLE);
                tv_header_state.setText(text_pull_down_refresh);
            }
        } else {
            LogUtil.e("非法状态，不能设置");
        }
    }

    public void setFooterFunction(FooterFunction footerFunction) {
        if (load_state == load_state_up_load || load_state == load_state_only_display) {
            load_state = footerFunction.getFooterState();
            if (load_state == load_state_only_display) {
                tv_footer_state.setText(footerTextOnOnlyDisplay);
                iv_footer_right.setVisibility(View.INVISIBLE);
            } else if (load_state == load_state_up_load) {
                iv_footer_right.setVisibility(View.VISIBLE);
                tv_footer_state.setText(text_pull_up_load);
            }
        } else if (load_state == load_state_loading || load_state == load_state_finished) {
            setOnLoadMoreFinishedResetListener(refreshLayout -> {
                load_state = footerFunction.getFooterState();
                if (load_state == load_state_only_display) {
                    iv_footer_right.setVisibility(View.INVISIBLE);
                    tv_footer_state.setText(footerTextOnOnlyDisplay);
                }
            });

        } else if (load_state == load_state_mutex) {
            setOnLoadMoreFinishedResetListener(refreshLayout -> load_state = footerFunction.getFooterState());
        } else {
            LogUtil.e("非法状态");
        }
    }

    private OnRefreshListener onRefreshListener;

    private OnLoadMoreListener onLoadMoreListener;
    private OnLoadMoreFinishedResetListener onLoadMoreFinishedResetListener;
    private OnRefreshFinishedResetListener onRefreshFinishedResetListener;


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
        StackTraceElement element = Thread.currentThread().getStackTrace()[3];
        key_refresh_last_update = key_refresh_last_update + element.getClassName() + "_" + element.getMethodName();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /**
     * 每次执行完毕后 监听对象会被移除
     */
    @SuppressWarnings("unused")
    public void setOnLoadMoreFinishedResetListener(OnLoadMoreFinishedResetListener listener) {
        this.onLoadMoreFinishedResetListener = listener;
    }

    @SuppressWarnings("unused")
    public void setRefreshFinishedResetListener(OnRefreshFinishedResetListener listener) {
        this.onRefreshFinishedResetListener = listener;
    }

    public void notifyLoadMoreFinish() {
        if (load_state == load_state_loading) {
            animUpdateState(getScrollY(), getScrollY(), load_state_finished, false);
        }
    }

    @SuppressWarnings("unused")
    public void notifyRefreshFinish() {
        if (refresh_state == refresh_state_refreshing) {
            animUpdateState(getScrollY(), getScrollY(), refresh_state_finished, true);
        }
    }

    @SuppressWarnings("unused")
    public void notifyLoadFinish() {
        notifyLoadMoreFinish();
        notifyRefreshFinish();
    }
}