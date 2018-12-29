package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewAdapter;
import com.common.adapter.common.RecyclerViewUtil;
import com.common.base.BaseActivity;
import com.common.helper.RefreshHelper;
import com.common.utils.TestDataUtil;
import com.common.widget.AsyncScrollLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * Author: Pan
 * Description:
 */
public class ForegroundDrawingTestActivity extends BaseActivity {

    private TextViewAdapter adapter_1;
    private TextViewAdapter adapter_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SmartRefreshLayout smt_refresh = findViewById(R.id.smt_refresh);
        AsyncScrollLayout async_scroll_view = findViewById(R.id.async_scroll_view);
        RecyclerView recycler_view_1 = findViewById(R.id.recycler_view_1);
        RecyclerView recycler_view_2 = findViewById(R.id.recycler_view_2);
        RefreshHelper.setSmartRefreshLayout(smt_refresh, new RefreshHelper.CallbackAdapter() {
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
        });

        adapter_1 = new TextViewAdapter(activity,
                R.layout.view_tv_1, TestDataUtil.getData(300));
        adapter_2 = new TextViewAdapter(activity,
                R.layout.view_tv_2, TestDataUtil.getData(300));


        RecyclerViewUtil.setRecyclerView(recycler_view_1, adapter_1);
        RecyclerViewUtil.setRecyclerView2(recycler_view_2, adapter_2);

        async_scroll_view.addRecyclerViewGroup(recycler_view_2, recycler_view_1);
        // adapter_1.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
        //   adapter_2.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fore_draw_test;
    }
}
