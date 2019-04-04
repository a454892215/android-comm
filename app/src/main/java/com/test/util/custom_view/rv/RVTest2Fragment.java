package com.test.util.custom_view.rv;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.refresh.RefreshLayout;
import com.common.widget.rv.AniDecoration;
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
        //  BaseItemAnimator animator = new BaseItemAnimator();
        //  animator.setChangeDuration(10);
        // rv.setItemAnimator(animator);
        AniDecoration aniDecoration = new AniDecoration();
        rv.addItemDecoration(aniDecoration);
        BaseAppRVAdapter adapter = RVHelper.initRV(activity, TestEntity.getList(), rv, RVTestAdapter.class);
        adapter.setOnItemClick((view, position) -> aniDecoration.playAnimation(rv));
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(refreshLayout -> refresh_layout.postDelayed(() -> {
            refresh_layout.notifyLoadFinish();
            RVHelper.notifyAdapterRefresh(TestEntity.getList(), rv);
        }, 800));
    }
}
