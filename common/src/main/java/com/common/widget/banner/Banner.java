package com.common.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseActivity;
import com.common.helper.RVHelper;
import com.common.utils.MathUtil;

import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2019/6/17 9:57
 * Description: No
 */

public class Banner extends RecyclerView {
    public Banner(@NonNull Context context) {
        this(context, null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void initView(BaseActivity activity, List<String> urlList) {
        new PagerSnapHelper().attachToRecyclerView(this);
        RVHelper.initHorizontalRV(activity, urlList, this, BannerAdapter.class);
        this.scrollToPosition(Integer.MAX_VALUE / 2 + urlList.size() - 4);
        this.smoothScrollToPosition(Integer.MAX_VALUE / 2 + urlList.size() - 3);//跳到中间的第一张图片
        this.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private float leftOfCenterView;
            private float leftOfRightView;
            private boolean isHasIDLE = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (leftOfCenterView == 0 && isHasIDLE && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    leftOfCenterView = recyclerView.getChildAt(1).getLeft();
                    leftOfRightView = recyclerView.getChildAt(2).getLeft();
                    onScrolled(recyclerView, 0, 0);
                    // LogUtil.d("=====onScrollStateChanged:" + "  leftOfCenterView:" + leftOfCenterView + "  leftOfRightView:" + leftOfRightView);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) isHasIDLE = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
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
                        if (child instanceof ViewGroup) {
                            View imgView = ((ViewGroup) child).getChildAt(0);
                            if (imgView instanceof ImageView) {
                                imgView.setScaleX(scale);
                                imgView.setScaleY(scale);
                            }
                        }
                    }
                }

            }
        });
    }
}
