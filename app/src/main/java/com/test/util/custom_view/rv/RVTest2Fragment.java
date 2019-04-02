package com.test.util.custom_view.rv;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.refresh.RefreshLayout;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestAdapter;

import java.util.List;
import java.util.Map;

public class RVTest2Fragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test2;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        BaseAppRVAdapter adapter = RVHelper.initRV(activity, TestEntity.getList(), rv, RVTestAdapter.class);
        adapter.setOnItemClick((view, position) -> rv.postDelayed(() -> {
            List<Map<String, String>> list = adapter.getList();
            list.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(0, list.size());
        }, 500));
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(), rv);
        }, 800));
    }
}
