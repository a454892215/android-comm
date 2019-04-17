package com.test.util.custom_view.rv;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.SwipeLayout;
import com.common.widget.refresh.RefreshLayout;
import com.test.util.R;
import com.test.util.custom_view.rv.adapter.SwipeTestAdapter;

public class RVTest3Fragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test3;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        RVHelper.initVerticalRV(activity, TestEntity.getList(20), rv, SwipeTestAdapter.class);
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
       // refresh_layout.setInterceptEventOnScrolled(true);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            SwipeLayout.clearOPenPosition(rv);
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(20), rv);
        }, 800));
    }

}
