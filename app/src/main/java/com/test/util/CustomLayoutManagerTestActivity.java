package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewRVAdapter;
import com.common.adapter.common.HVLayoutManager;
import com.common.base.BaseActivity;
import com.common.base.BaseRVAdapter;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;

/**
 * RecyclerView
 */
public class CustomLayoutManagerTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView rv = findViewById(R.id.recycler_view_1);
        HVLayoutManager layoutManager = new HVLayoutManager(5);
        rv.setLayoutManager(layoutManager);
        BaseRVAdapter adapter = new TextViewRVAdapter(activity, R.layout.view_tv_1, TestDataUtil.getData(50));
        rv.setAdapter(adapter);
        adapter.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cus_lay_manager_test;
    }
}
