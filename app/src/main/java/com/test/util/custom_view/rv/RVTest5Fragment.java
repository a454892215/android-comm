package com.test.util.custom_view.rv;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;

import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.rv.RightSlideInAniDecoration;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestHorizontalAdapter;


public class RVTest5Fragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_test5;
    }


    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        RightSlideInAniDecoration aniDecoration = new RightSlideInAniDecoration();
        rv.addItemDecoration(aniDecoration);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        rv.setLayoutManager(manager);
        RVHelper.initHorizontalRV(activity, TestEntity.getList(), rv, RVTestHorizontalAdapter.class);

        findViewById(R.id.btn).setOnClickListener(v -> {
            rv.smoothScrollBy(450, 450);
        });
    }

}
