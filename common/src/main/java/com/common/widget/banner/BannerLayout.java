package com.common.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.R;
import com.common.utils.LogUtil;

import java.util.List;

/**
 * Author:  L
 * CreateDate: 2019/6/17 9:57
 * Description: 基于RecyclerView的 Banner
 */
@Deprecated // 嵌套RV ViewPager使用会有问题
@SuppressWarnings("unused")
public class BannerLayout extends FrameLayout {
    private Context context;
    private LinearLayout indicatorParentView;
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

    private void init() {
        if (rv == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_banner, this, true);
            rv = view.findViewById(R.id.rv);
            indicatorParentView = view.findViewById(R.id.llt_indicators);
            new PagerSnapHelper().attachToRecyclerView(rv);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rv.setLayoutManager(linearLayoutManager);
            bannerAdapter = new BannerAdapter(context);
            rv.setAdapter(bannerAdapter);
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
    }


    private LoopTask loopTask = new LoopTask();

    private class LoopTask implements Runnable {
        @Override
        public void run() {
            View child = rv.getChildAt(0);
            if (child != null) {
                rv.smoothScrollBy(child.getWidth(), 0, new  ViscousFluidInterpolator(), 500);
            }
            postDelayed(loopTask, 3000);
        }
    }

    public void play(long delay) {
        postDelayed(loopTask, delay);
    }

    public void stop() {
        removeCallbacks(loopTask);
    }

    private boolean isNeedDefIndicator = true;

    public BannerLayout updateBanner(List<Object> urlList) {
        if (rv == null) init();
        bannerCount = urlList.size();
        indicatorParentView.removeAllViews();
        if (isNeedDefIndicator) {
            for (int i = 0; i < bannerCount; i++) {
                LayoutInflater.from(context).inflate(R.layout.layout_banner_indicator, indicatorParentView, true);
            }
        }
        bannerAdapter.getList().clear();
        bannerAdapter.getList().addAll(urlList);
        bannerAdapter.notifyDataSetChanged();
        int centerPos = Integer.MAX_VALUE / 2;
        rv.scrollToPosition(centerPos - (centerPos % bannerCount));
        return this;
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
        LogUtil.d("=======curPagerIndex:" + curPagerIndex);
        int indicatorCount = indicatorParentView.getChildCount();
        for (int j = 0; j < indicatorCount; j++) {
            indicatorParentView.getChildAt(j).setBackgroundColor(defaultIndicatorColor);
        }
        View curView = indicatorParentView.getChildAt(curPagerIndex);
        if (curView != null) {
            curView.setBackgroundColor(showingIndicatorColor);
        }
        if (onBannerChangedListener != null) {
            onBannerChangedListener.onChanged(curPagerIndex);
        }
    }

    public void setShowingIndicatorColor(int showingIndicatorColor) {
        this.showingIndicatorColor = showingIndicatorColor;
    }

    public void setDefaultIndicatorColor(int defaultIndicatorColor) {
        this.defaultIndicatorColor = defaultIndicatorColor;
    }

    public RecyclerView getRv() {
        return rv;
    }

    public LinearLayout getIndicatorParentView() {
        return indicatorParentView;
    }

    public void setNeedDefIndicator(boolean needDefIndicator) {
        isNeedDefIndicator = needDefIndicator;
    }

    private OnBannerChangedListener onBannerChangedListener;

    public void setNeedDefIndicator(OnBannerChangedListener listener) {
        onBannerChangedListener = listener;
    }

    public interface OnBannerChangedListener {
        void onChanged(int curPagerIndex);
    }

}
