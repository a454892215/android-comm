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
import com.common.comm.timer.MyTimer;
import com.common.utils.LogUtil;

import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2019/6/17 9:57
 * Description: No
 */

public class BannerLayout extends FrameLayout {
    private Context context;
    private LinearLayout llt_indicators;
    private RecyclerView rv;
    private MyTimer downTimer;

    public BannerLayout(@NonNull Context context) {
        this(context, null);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
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
        rv.scrollToPosition(Integer.MAX_VALUE / 2 + urlList.size() - 3);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                View child0 = recyclerView.getChildAt(0);
                if (child0 != null) {
                    updateIndicator(child0, urlList.size());
                }
            }
        });
    }

    private boolean loopScrollEnable;

    private void startLoopScroll() {
        if (downTimer == null) {
            downTimer = new MyTimer(Long.MAX_VALUE, 3000);
            downTimer.setOnTickListener((time, count) -> {
                View child = rv.getChildAt(0);
                if (child != null && !isPressing && child.getLeft() == 0 && System.currentTimeMillis() - lastUpTime > 2000) {
                    rv.smoothScrollBy(child.getWidth(), 0, new DecelerateInterpolator(), 500);
                }
            });
        }
        downTimer.start(); //不能延迟执行
    }

    private boolean isPressing;

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == View.VISIBLE) {
            if (loopScrollEnable) startLoopScroll();
        } else {
            if (downTimer != null) downTimer.cancel();
        }
    }

    private long lastUpTime = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPressing = true;
                break;
            case MotionEvent.ACTION_UP:
                isPressing = false;
                lastUpTime = System.currentTimeMillis();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private int showingIndicatorColor = Color.RED;

    private int defaultIndicatorColor = Color.WHITE;

    private void updateIndicator(View child, int urlListSize) {
        int adapterPosition = rv.getChildAdapterPosition(child);
        int indicatorCount = llt_indicators.getChildCount();
        for (int j = 0; j < indicatorCount; j++) {
            llt_indicators.getChildAt(j).setBackgroundColor(defaultIndicatorColor);
        }
        llt_indicators.getChildAt(adapterPosition % urlListSize).setBackgroundColor(showingIndicatorColor);
    }

    public void setShowingIndicatorColor(int showingIndicatorColor) {
        this.showingIndicatorColor = showingIndicatorColor;
    }

    public void setDefaultIndicatorColor(int defaultIndicatorColor) {
        this.defaultIndicatorColor = defaultIndicatorColor;
    }
}
