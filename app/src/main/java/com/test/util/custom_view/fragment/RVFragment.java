package com.test.util.custom_view.fragment;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.MyItemDecoration;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestAdapter;

public class RVFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_anim;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.addItemDecoration(new MyItemDecoration());
        RVHelper.initRV(activity, TestEntity.getList(),rv , RVTestAdapter.class);
    }
}
