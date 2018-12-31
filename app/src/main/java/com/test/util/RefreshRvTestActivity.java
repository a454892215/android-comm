package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewRVAdapter;
import com.common.adapter.common.HLayoutManager;
import com.common.base.BaseActivity;
import com.common.utils.TestDataUtil;
import com.common.widget.AsyncScrollLayout;
import com.common.widget.RefreshLayout;

/**
 * Author: Pan
 * Description:
 */
public class RefreshRvTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RefreshLayout refreshLayout = findViewById(R.id.refresh_layout);
        AsyncScrollLayout async_scroll_view = findViewById(R.id.async_scroll_view);
        RecyclerView recycler_view_1 = findViewById(R.id.recycler_view_1);
        RecyclerView recycler_view_2 = findViewById(R.id.recycler_view_2);
/*        RefreshHelper.setSmartRefreshLayout(smt_refresh, new RefreshHelper.CallbackAdapter() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                ((SmartRefreshLayout) refreshLayout).postDelayed(refreshLayout::finishRefresh, 500);
                adapter_1.getList().clear();
                adapter_2.getList().clear();
                adapter_1.getList().addAll(TestDataUtil.getData(40));
                adapter_2.getList().addAll(TestDataUtil.getData(40));
                adapter_1.notifyDataSetChanged();
                adapter_2.notifyDataSetChanged();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                ((SmartRefreshLayout) refreshLayout).postDelayed(refreshLayout::finishLoadMore, 500);
                List<String> list = adapter_1.getList();
                adapter_1.getList().addAll(TestDataUtil.getData(40, list.size()));
                adapter_2.getList().addAll(TestDataUtil.getData(40, list.size()));
                adapter_1.notifyDataSetChanged();
                adapter_2.notifyDataSetChanged();
            }
        });*/

        TextViewRVAdapter adapter_1 = new TextViewRVAdapter(activity,
                R.layout.view_tv_1, TestDataUtil.getData(50));
        TextViewRVAdapter adapter_2 = new TextViewRVAdapter(activity,
                R.layout.view_tv_2, TestDataUtil.getData(50));

        recycler_view_1.setLayoutManager( new HLayoutManager(activity));
        recycler_view_1.setAdapter(adapter_1);

        recycler_view_2.setLayoutManager( new HLayoutManager(activity));
        recycler_view_2.setAdapter(adapter_2);

        async_scroll_view.addRecyclerViewGroup(recycler_view_2, recycler_view_1);
        // adapter_1.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
        //   adapter_2.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh_rv_test;
    }
}
