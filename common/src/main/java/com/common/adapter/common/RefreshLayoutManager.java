package com.common.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.widget.RefreshLayout;

/**
 * Author:  Pan
 * CreateDate: 2018/12/28
 * Description: No
 */

public class RefreshLayoutManager extends HLayoutManager {

    private RefreshLayout refreshLayout;

    public RefreshLayoutManager(Context context, RefreshLayout refreshLayout) {
        super(context);
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int real_dy = super.scrollVerticallyBy(dy, recycler, state);
        // LogUtil.d(" real_dy: " + real_dy + "  dy:" + dy);
        return real_dy;
    }

    RecyclerView recyclerView;

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        this.recyclerView = view;

    }
}
