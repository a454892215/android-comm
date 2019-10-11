package com.common.widget.banner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.R;
import com.common.base.BaseActivity;
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

    public void initView(BaseActivity activity, List<String> urlList) {
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
        BannerAdapter adapter = new BannerAdapter(activity, urlList);
        rv.setAdapter(adapter);

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
                        leftOfCenterView = recyclerView.getChildAt(1).getLeft();
                        leftOfRightView = recyclerView.getChildAt(2).getLeft();
                        onScrolled(recyclerView, 0, 0);
                        // LogUtil.d("=====onScrollStateChanged:" + "  leftOfCenterView:" + leftOfCenterView + "  leftOfRightView:" + leftOfRightView);
                    }
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) isHasIDLE = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("============发生异常：" + e);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                try {
                    if (leftOfCenterView != 0) {
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
                            updateIndicator(child, kjd, urlList);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.e("============发生异常：" + e);
                }

            }
        });
    }

    private void updateScaleView(View child, float scale) {
        if (scaleEnable && child instanceof ViewGroup) {
            View imgView = ((ViewGroup) child).getChildAt(0);
            if (imgView instanceof ImageView) {
                imgView.setScaleX(scale);
                imgView.setScaleY(scale);
            }
        }
    }

    private int showingIndicatorColor = Color.RED;

    private int defaultIndicatorColor = Color.WHITE;

    private boolean scaleEnable = true;

    private void updateIndicator(View child, float kjd, List<String> urlList) {
        if (kjd == 0) {
            int adapterPosition = rv.getChildAdapterPosition(child);
            int indicatorCount = llt_indicators.getChildCount();
            for (int j = 0; j < indicatorCount; j++) {
                llt_indicators.getChildAt(j).setBackgroundColor(defaultIndicatorColor);
            }
            llt_indicators.getChildAt(adapterPosition % urlList.size()).setBackgroundColor(showingIndicatorColor);
        }
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
