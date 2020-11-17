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
    private int bannerCount;
    private BannerAdapter bannerAdapter;

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

    public void init(BaseActivity activity, List<String> urlList) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
        rv = view.findViewById(R.id.rv);
        llt_indicators = view.findViewById(R.id.llt_indicators);
        bannerCount = urlList.size();
        for (int i = 0; i < bannerCount; i++) {
            LayoutInflater.from(context).inflate(R.layout.layout_banner_indicator, llt_indicators, true);
        }
        new PagerSnapHelper().attachToRecyclerView(rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        bannerAdapter = new BannerAdapter(activity, urlList);
        rv.setAdapter(bannerAdapter);
        rv.scrollToPosition(Integer.MAX_VALUE / 2 + bannerCount - 3);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View child0 = recyclerView.getChildAt(0);
                View child1 = recyclerView.getChildAt(1);
                float validInRate = 1f / 3f;
                //第1个itemView 从左边进入， 进入超过2/3， 计入有效入屏 此时: child0.left > -width * 1/3f
                if (child0 != null && child0.getLeft() > -child0.getWidth() * validInRate) {
                    checkCurrentPager(child0, bannerCount);
                }
                //第2个itemView 从右边进入， 进入超过2/3， 计入有效入屏 此时: child1.left < width * 1/3f
                if (child1 != null && child1.getLeft() < child1.getWidth() * validInRate) {
                    checkCurrentPager(child1, bannerCount);
                }
            }
        });

    }


    private LoopTask loopTask = new LoopTask();

    private class LoopTask implements Runnable {
        @Override
        public void run() {
            View child = rv.getChildAt(0);
            if (child != null) {
                rv.smoothScrollBy(child.getWidth(), 0, new DecelerateInterpolator(), 500);
            }
            postDelayed(loopTask, 3000);
        }
    }


    public long lastUpTime = 0;

    public void play(long delay) {
        postDelayed(loopTask, delay);
    }

    public void stop() {
        removeCallbacks(loopTask);
    }

    public void updateBannerUrl(List<String> urlList) {
        bannerAdapter.getList().clear();
        bannerAdapter.getList().addAll(urlList);
        bannerAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                play(3000);
                lastUpTime = System.currentTimeMillis();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private int showingIndicatorColor = Color.RED;

    private int defaultIndicatorColor = Color.WHITE;

    private int lastPageIndex = -1;

    private void checkCurrentPager(View curItemView, int urlListSize) {
        int adapterPosition = rv.getChildAdapterPosition(curItemView);
        int pageIndex = adapterPosition % urlListSize;
        if (pageIndex != lastPageIndex) {
            updateIndicator(pageIndex);
            lastPageIndex = pageIndex;
        }
    }

    private void updateIndicator(int curPagerIndex) {
        int indicatorCount = llt_indicators.getChildCount();
        for (int j = 0; j < indicatorCount; j++) {
            llt_indicators.getChildAt(j).setBackgroundColor(defaultIndicatorColor);
        }
        llt_indicators.getChildAt(curPagerIndex).setBackgroundColor(showingIndicatorColor);
    }

    public void setShowingIndicatorColor(int showingIndicatorColor) {
        this.showingIndicatorColor = showingIndicatorColor;
    }

    public void setDefaultIndicatorColor(int defaultIndicatorColor) {
        this.defaultIndicatorColor = defaultIndicatorColor;
    }
}
