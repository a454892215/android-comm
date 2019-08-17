package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;

public class TrendChartFragment_04 extends BaseFragment {
    String[] tabNames = {"走势图一", "走势图二"};
    Class[] fragmentArr = {TrendChartFragment_1.class, TrendChartFragment_2.class};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_chart;
    }

    @Override
    protected void initView() {
        CommonTabLayout tab_layout_1 = findViewById(R.id.tab_layout_1);
        tab_layout_1.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout_1.setData(tabNames, R.layout.template_hor_scroll_tab_item_1_a, R.id.tv);
        tab_layout_1.setOnSelectChangedListener(position -> FragmentHelper.onSwitchFragment(fm, fragmentArr, position, R.id.flt_content, true));
        tab_layout_1.setCurrentPosition(0);
    }
}
