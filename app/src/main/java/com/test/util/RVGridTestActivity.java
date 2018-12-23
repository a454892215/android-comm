package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewAdapter;
import com.common.base.BaseActivity;
import com.common.base.BaseRecyclerViewAdapter;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;

/**
 * RecyclerView
 */
public class RVGridTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler_view_1 = findViewById(R.id.recycler_view_1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity,3);
        recycler_view_1.setLayoutManager(gridLayoutManager);
        BaseRecyclerViewAdapter adapter_1 = new TextViewAdapter(activity, R.layout.view_tv_1, TestDataUtil.getData(500));
        recycler_view_1.setAdapter(adapter_1);
        adapter_1.setOnItemClick((itemView, position) -> {
            ToastUtil.showShort(activity, ":" + position);
            recycler_view_1.scrollBy(10,0);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rev_grid_test;
    }
}
