package com.common.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.R;
import com.common.base.BaseActivity;
import com.common.comm.timer.MyCountDownTimer;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;

import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2019/6/17 9:57
 * Description: No
 */

public class Banner extends FrameLayout {
    private Context context;
    private LinearLayout llt_indicators;
    private RecyclerView rv;
    private MyCountDownTimer downTimer;

    public Banner(@NonNull Context context) {
        this(context, null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }


    public RecyclerView getRv() {
        return rv;
    }

    public void init(BaseActivity activity, List<String> urlList, boolean loopScrollEnable) {
        this.loopScrollEnable = loopScrollEnable;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
        rv = view.findViewById(R.id.rv);
        llt_indicators = view.findViewById(R.id.llt_indicators);
        for (int i = 0; i < urlList.size(); i++) {
            LayoutInflater.from(context).inflate(R.layout.layout_banner_indicator, llt_indicators, true);
        }
        new PagerSnapHelper().attachToRecyclerView(rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new BannerAdapter(activity, urlList));

        rv.scrollToPosition(Integer.MAX_VALUE / 2 + urlList.size() - 4);
        rv.smoothScrollToPosition(Integer.MAX_VALUE / 2 + urlList.size() - 3);//跳到中间的第一张图片
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private float leftOfCenterView;
            private float leftOfRightView;
            private boolean isHasIDLE = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    if (leftOfCenterView == 0 && isHasIDLE && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        View child_1 = recyclerView.getChildAt(1);
                        if (child_1 != null) {
                            leftOfCenterView = child_1.getLeft();
                        }
                        View child_2 = recyclerView.getChildAt(2);
                        if (child_2 != null) {
                            leftOfRightView = child_2.getLeft();
                        }
                        onScrolled(recyclerView, 0, 0);
                        // LogUtil.d("=====onScrollStateChanged:" + "  leftOfCenterView:" + leftOfCenterView + "  leftOfRightView:" + leftOfRightView);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) isHasIDLE = true;
                } catch (Exception e) {
                    LogUtil.e(e);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                try {
                    int childCount = recyclerView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = recyclerView.getChildAt(i);
                        float kjd = (child.getLeft() - leftOfCenterView); // 靠近度
                        //靠近率 为0时，最靠近。值域[-1,1]
                        float kjl = MathUtil.clamp(kjd / (leftOfRightView - leftOfCenterView), -1, 1);
                        float scale = 1 - Math.abs(kjl); //缩放 [1,0]
                        scale = (scale + 9f) / 10f;// [1,0] - > [1,0.9]
                        scale = MathUtil.clamp(scale, 0f, 1f);
                        updateScaleView(child, scale);
                        updateIndicator(child, urlList);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("============发生异常：" + e);
                }

            }
        });
    }

    private boolean loopScrollEnable;

    private void setLoopScroll(int delay) {
        if (downTimer != null) downTimer.cancel();
        downTimer = new MyCountDownTimer(Integer.MAX_VALUE, 3000);
        downTimer.setOnTickListener((time, count) -> {
            View child = rv.getChildAt(0);
            if (child != null && !isPressing && child.getLeft() == 0) {
                rv.smoothScrollBy(child.getWidth(), 0, new DecelerateInterpolator(), 500);
            }
        });
        rv.postDelayed(downTimer::start, delay);
    }

    private void updateScaleView(View child, float scale) {
        if (scaleEnable && child != null) {
            child.setScaleX(scale);
            child.setScaleY(scale);
        }
    }

    private boolean isPressing;

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            if (loopScrollEnable) setLoopScroll(2000);
        } else {
            if (downTimer != null) downTimer.cancel();
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressing = true;
                break;
            case MotionEvent.ACTION_UP:
                isPressing = false;
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private int showingIndicatorColor = Color.RED;

    private int defaultIndicatorColor = Color.WHITE;

    private boolean scaleEnable = false;

    private void updateIndicator(View child, List<String> urlList) {
        int adapterPosition = rv.getChildAdapterPosition(child);
        int indicatorCount = llt_indicators.getChildCount();
        for (int j = 0; j < indicatorCount; j++) {
            llt_indicators.getChildAt(j).setBackgroundColor(defaultIndicatorColor);
        }
        llt_indicators.getChildAt(adapterPosition % urlList.size()).setBackgroundColor(showingIndicatorColor);
    }


    public void setScaleEnable(boolean scaleEnable) {
        this.scaleEnable = scaleEnable;
    }

    public void setShowingIndicatorColor(int showingIndicatorColor) {
        this.showingIndicatorColor = showingIndicatorColor;
    }

    public void setDefaultIndicatorColor(int defaultIndicatorColor) {
        this.defaultIndicatorColor = defaultIndicatorColor;
    }
}
