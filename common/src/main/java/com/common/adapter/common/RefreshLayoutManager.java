package com.common.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.common.utils.LogUtil;
import com.common.widget.RefreshLayout;

/**
 * Author:  Pan
 * CreateDate: 2018/12/28
 * Description: No
 */

public class RefreshLayoutManager extends HLayoutManager {

    private RefreshLayout refreshLayout;
    private final View headerView;

    public RefreshLayoutManager(Context context, RefreshLayout refreshLayout) {
        super(context);
        this.refreshLayout = refreshLayout;
        headerView = refreshLayout.getHeaderView();
    }



    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int real_dy = super.scrollVerticallyBy(dy, recycler, state);
       // LogUtil.d(" real_dy: " + real_dy + "  dy:" + dy);
        if (real_dy == 0) { //RV没有滚动
            if (dy < 0) { //向下滚动
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) headerView.getLayoutParams();
                lp.topMargin += -dy;
                if (lp.topMargin > 0) {
                    lp.topMargin = 0;
                }
                headerView.setLayoutParams(lp);
            }
        }
        return real_dy;
    }
}
