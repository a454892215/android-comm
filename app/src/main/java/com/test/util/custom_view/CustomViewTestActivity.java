package com.test.util.custom_view;

import android.os.Bundle;

import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;
import com.test.util.custom_view.fragment.CityPickerFragment_02;
import com.test.util.custom_view.fragment.DialogTestFragment_03;
import com.test.util.custom_view.fragment.FloatWindowFragment_06;
import com.test.util.custom_view.fragment.TestFragment_07;
import com.test.util.custom_view.fragment.RVFragment_05;
import com.test.util.custom_view.fragment.TabFragment_01;
import com.test.util.custom_view.fragment.TrendChartFragment_04;

public class CustomViewTestActivity extends BaseAppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test;
    }

    Class[] fragmentArr = {TabFragment_01.class, CityPickerFragment_02.class, DialogTestFragment_03.class, TrendChartFragment_04.class, RVFragment_05.class, FloatWindowFragment_06.class
            , TestFragment_07.class};
    String[] tabNames = {"Tab和速度", "Picker和TV", "弹窗", "Chart", "RV相关", "悬浮窗", "Transition"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout_1);
        tab_layout.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        FragmentHelper fragmentHelper = new FragmentHelper(fm, fragmentArr, R.id.flt_content);
        tab_layout.setOnSelectChangedListener(position -> fragmentHelper.onSwitchFragment(position, true));
        tab_layout.setCurrentPosition(0);
    }
}
