package com.test.util;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.common.helper.FragmentHelper;
import com.common.utils.ViewUtil;
import com.common.widget.CommonTabLayout;
import com.test.util.base.BaseAppActivity;
import com.test.util.fragment.CityPickerFragment;
import com.test.util.fragment.DialogTestFragment;
import com.test.util.fragment.RVAnimFragment;
import com.test.util.fragment.TrendChartFragment;

public class CustomViewTestActivity extends BaseAppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test;
    }

    Class[] fragmentArr = {RVAnimFragment.class, CityPickerFragment.class, DialogTestFragment.class, TrendChartFragment.class};
    String[] tabNames = {"RV动画测试", "城市选择", "DialogTest", "Chart"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flt_content = findViewById(R.id.flt_content);
        ViewUtil.onlyShowOneChildView(flt_content, 0);
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setIndicatorView(findViewById(R.id.view_tab_indicator), Math.round(dp_1 * 100));
        tab_layout.setData(tabNames, R.layout.layout_tab_item, R.id.tv);
        tab_layout.setOnSelectChangedListener(position -> FragmentHelper.onSwitchFragment(fm, fragmentArr, position, R.id.flt_content));
        tab_layout.setCurrentPosition(0);
    }


}
