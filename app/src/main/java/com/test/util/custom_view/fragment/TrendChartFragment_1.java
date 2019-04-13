package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.widget.CommonTabLayout;
import com.test.util.R;

public class TrendChartFragment_1 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_chart_1;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"组合走势图1", "组合走势图2", "组合走势图3", "组合走势图4"};


        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        tab_layout_2.setOnSelectChangedListener(position -> {

        });


       // tab_layout_2.setCurrentPosition(0);
    }
}
