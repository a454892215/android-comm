package com.common.adapter.common;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.common.R;

/**
 * Author:  Pan
 * CreateDate: 2018/12/28
 * Description: No
 */

public class ForegroundManager extends HLayoutManager {

    private View foregroundView;

    public ForegroundManager(Context context) {
        super(context);
        foregroundView = LayoutInflater.from(context).inflate(R.layout.view_tv_3, null);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        int width = getWidth();
        int height = getHeight();
        addView(foregroundView);
        layoutDecorated(foregroundView, 0, 0, width, height);
    }

}
