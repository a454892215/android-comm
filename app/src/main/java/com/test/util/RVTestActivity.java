package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewAdapter;
import com.common.adapter.common.RecyclerViewUtil;
import com.common.base.BaseActivity;
import com.common.base.BaseRecyclerViewAdapter;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;

public class RVTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler_view_1 = findViewById(R.id.recycler_view_1);
        RecyclerView recycler_view_2 = findViewById(R.id.recycler_view_2);

        BaseRecyclerViewAdapter adapter_1 = new TextViewAdapter(activity,
                R.layout.view_tv_1, TestDataUtil.getData(50));
        BaseRecyclerViewAdapter adapter_2 = new TextViewAdapter(activity,
                R.layout.view_tv_1, TestDataUtil.getData(50));

        RecyclerViewUtil.setRecyclerView(recycler_view_1, adapter_1);
        RecyclerViewUtil.setRecyclerView(recycler_view_2, adapter_2);

        RecyclerViewUtil.syncScroll(recycler_view_1, recycler_view_2);
        adapter_1.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
        adapter_2.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rev_test;
    }
}
