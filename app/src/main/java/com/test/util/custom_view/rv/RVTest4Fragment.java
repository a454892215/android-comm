package com.test.util.custom_view.rv;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.utils.LogUtil;
import com.common.widget.refresh.RefreshLayout;
import com.test.util.R;
import com.test.util.custom_view.rv.adapter.SnapTestAdapter;

public class RVTest4Fragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test4;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        //    new LinearSnapHelper().attachToRecyclerView(rv);
        new PagerSnapHelper().attachToRecyclerView(rv);
        RVHelper.initHorizontalRV(activity, TestEntity.getList(), rv, SnapTestAdapter.class);
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setInterceptEventOnScrolled(true);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(), rv);
        }, 800));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LogUtil.d("===============dx:" + dx + "dy:" + dy + "  :"+recyclerView.getChildCount());
            }
        });
    }

}
