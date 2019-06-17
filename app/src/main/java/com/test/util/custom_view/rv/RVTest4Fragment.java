package com.test.util.custom_view.rv;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;
import com.common.widget.refresh.RefreshLayout;
import com.test.util.R;
import com.test.util.custom_view.rv.adapter.SnapTestAdapter;

public class RVTest4Fragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test4;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        //    new LinearSnapHelper().attachToRecyclerView(rv);
        new PagerSnapHelper().attachToRecyclerView(rv);
        RVHelper.initHorizontalRV(activity, null, rv, SnapTestAdapter.class);
        rv.scrollToPosition(Integer.MAX_VALUE / 2 + 1);
        rv.smoothScrollToPosition(Integer.MAX_VALUE / 2 + 2);
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setInterceptEventOnScrolled(true);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(), rv);
        }, 800));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private float leftOfCenterView;
            private float leftOfRightView;
            private boolean isHasIDLE = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (leftOfCenterView == 0 && isHasIDLE && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    leftOfCenterView = recyclerView.getChildAt(1).getLeft();
                    leftOfRightView = recyclerView.getChildAt(2).getLeft();
                    LogUtil.d("=====onScrollStateChanged:" + "  leftOfCenterView:" + leftOfCenterView + "  leftOfRightView:" + leftOfRightView);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) isHasIDLE = true;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (leftOfCenterView != 0) {
                    int childCount = recyclerView.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = recyclerView.getChildAt(i);
                        float kjd = (child.getLeft() - leftOfCenterView); // 靠近度
                        //靠近率 为0时，最靠近。值域[-1,1]
                        float kjl = MathUtil.clamp(kjd / (leftOfRightView - leftOfCenterView), -1, 1);
                        float scale = 1 - Math.abs(kjl); //缩放 [1,0]
                        scale = (scale + 9f)/10f;// [1,0] - > [1,0.9]
                        scale = MathUtil.clamp(scale,0f,1f);
                        if (child instanceof ViewGroup) {
                            View imgView = ((ViewGroup) child).getChildAt(0);
                            if (imgView instanceof ImageView) {
                                imgView.setScaleX(scale);
                                imgView.setScaleY(scale);
                            }
                        }

                        LogUtil.d("================kjd =:" + kjd + "  kjl:" + kjl + "  scale:" + scale);
                    }
                    LogUtil.d("=======================================>>>>>>:" + (activity.dp_1 * 280));
                }

            }
        });
    }

}
