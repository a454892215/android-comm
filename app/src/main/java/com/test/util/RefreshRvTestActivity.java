package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewRVAdapter;
import com.common.adapter.common.HLayoutManager;
import com.common.base.BaseActivity;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;
import com.common.widget.AsyncScrollLayout;
import com.common.widget.refresh.FooterFunction;
import com.common.widget.refresh.HeaderFunction;
import com.common.widget.refresh.RefreshLayout;

import java.util.List;

/**
 * Author: L
 * Description:
 */
public class RefreshRvTestActivity extends BaseActivity {

    private TextViewRVAdapter adapter_1;
    private TextViewRVAdapter adapter_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        AsyncScrollLayout async_scroll_view = findViewById(R.id.async_scroll_view);
        RecyclerView recycler_view_1 = findViewById(R.id.recycler_view_1);
        RecyclerView recycler_view_2 = findViewById(R.id.recycler_view_2);

        refreshLayout.setTargetView(recycler_view_1, recycler_view_2);
        refreshLayout.setHeaderFunction(HeaderFunction.refresh);
        refreshLayout.setFooterFunction(FooterFunction.load_more);
        refreshLayout.setAutoUpScrollEnableOnLoadMoreFinish(true);
        refreshLayout.setOnLoadMoreListener(refresh -> refreshLayout.postDelayed(() -> {
            List<String> list = adapter_1.getList();
            adapter_1.getList().addAll(TestDataUtil.getData(40, list.size()));
            adapter_2.getList().addAll(TestDataUtil.getData(40, list.size()));
            adapter_1.notifyDataSetChanged();
            adapter_2.notifyDataSetChanged();
            refreshLayout.notifyLoadMoreFinish();
            if (list.size() > 199) {
                refresh.setFooterTextOnOnlyDisplay("已经加载：" + list.size() + "条数据");
                refresh.setFooterFunction(FooterFunction.only_display);
            }
        }, 500));
        refreshLayout.setOnRefreshListener(refresh -> {

            adapter_1.getList().clear();
            adapter_2.getList().clear();
            adapter_1.getList().addAll(TestDataUtil.getData(40));
            adapter_2.getList().addAll(TestDataUtil.getData(40));
            adapter_1.notifyDataSetChanged();
            adapter_2.notifyDataSetChanged();
            refresh.setFooterFunction(FooterFunction.load_more);
            refresh.postDelayed(refreshLayout::notifyRefreshFinish, 500);
        });

        adapter_1 = new TextViewRVAdapter(activity,
                R.layout.view_tv_1, TestDataUtil.getData(40));
        adapter_2 = new TextViewRVAdapter(activity,
                R.layout.view_tv_2, TestDataUtil.getData(40));

        recycler_view_1.setLayoutManager(new HLayoutManager(activity));
        recycler_view_1.setAdapter(adapter_1);

        recycler_view_2.setLayoutManager(new HLayoutManager(activity));
        recycler_view_2.setAdapter(adapter_2);

        async_scroll_view.addRecyclerViewGroup(recycler_view_2, recycler_view_1);
        adapter_1.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
        adapter_2.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_rv_test;
    }
}
