package com.test.util.custom_view;

import android.os.Bundle;

import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;
import com.test.util.custom_view.fragment.CityPickerFragment;
import com.test.util.custom_view.fragment.DialogTestFragment;
import com.test.util.custom_view.fragment.FloatWinTestFragment;
import com.test.util.custom_view.fragment.RVFragment;
import com.test.util.custom_view.fragment.TabLayoutFragment;
import com.test.util.custom_view.fragment.TrendChartFragment;

public class CustomViewTestActivity extends BaseAppActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test;
    }

    Class[] fragmentArr = {TabLayoutFragment.class, CityPickerFragment.class, DialogTestFragment.class, TrendChartFragment.class, RVFragment.class, FloatWinTestFragment.class};
    String[] tabNames = {"Tab和速度", "Picker和TV", "弹窗", "Chart", "RV相关", "悬浮窗"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout_1);
        tab_layout.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        tab_layout.setOnSelectChangedListener(position -> FragmentHelper.onSwitchFragment(fm, fragmentArr, position, R.id.flt_content, true));
        tab_layout.setCurrentPosition(0);
    }
}
