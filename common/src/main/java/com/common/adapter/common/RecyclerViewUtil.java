package com.common.adapter.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.base.BaseRecyclerViewAdapter;

/**
 * Author:  Pan
 * CreateDate: 2018/12/18 9:14
 * Description: No
 */

public class RecyclerViewUtil {

    public static void setRecyclerView(RecyclerView rv, BaseRecyclerViewAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(rv.getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
    }
}
