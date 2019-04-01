package com.test.util;

import android.os.Bundle;

import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.base.BaseAppActivity;
import com.test.util.fragment.CityPickerFragment;
import com.test.util.fragment.DialogTestFragment;
import com.test.util.fragment.RVAnimFragment;
import com.test.util.fragment.TabLayoutFragment;
import com.test.util.fragment.TrendChartFragment;

public class CustomViewTestActivity extends BaseAppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test;
    }

    Class[] fragmentArr = {TabLayoutFragment.class, CityPickerFragment.class, DialogTestFragment.class, TrendChartFragment.class,RVAnimFragment.class};
    String[] tabNames = {"Tab Layout", "城市选择", "DialogTest", "Chart","Recycler相关"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout_1);
        tab_layout.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        tab_layout.setOnSelectChangedListener(position -> FragmentHelper.onSwitchFragment(fm, fragmentArr, position, R.id.flt_content));
        tab_layout.setCurrentPosition(0);
    }


}
