package com.test.util.custom_view.fragment;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.helper.RVHelper;
import com.common.test.TestEntity;
import com.common.widget.StickyHeaderDecoration;
import com.test.util.R;
import com.test.util.custom_view.adapter.RVTestAdapter;

import java.util.Arrays;

public class RVFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv_anim;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        Integer[] posArr = {5, 10, 20, 30, 34, 55, 78}; //有序数组
        String[] decorNameArr = {"5-title", "10-title", "20-title", "30-title", "34-title", "55-title", "78-title"};
        StickyHeaderDecoration decoration = new StickyHeaderDecoration(activity)
                .setDecorPositionList(Arrays.asList(posArr))
                .setDecorNameList(Arrays.asList(decorNameArr))
                .setHeaderBgColor(activity.getResources().getColor(R.color.light_purple));
        rv.addItemDecoration(decoration);
        RVHelper.initRV(activity, TestEntity.getList(), rv, RVTestAdapter.class);
    }
}
