package com.test.util.custom_view.rv;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.refresh.RefreshLayout;
import com.common.widget.rv.BottomSlideInAniDecoration;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestAdapter;

public class RVTest2Fragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test2;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        BottomSlideInAniDecoration aniDecoration = new BottomSlideInAniDecoration();
        rv.addItemDecoration(aniDecoration);
        RVHelper.initVerticalRV(activity, TestEntity.getList(), rv, RVTestAdapter.class);
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(), rv);
            aniDecoration.restartAnim();
        }, 800));
    }
}
