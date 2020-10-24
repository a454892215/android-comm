package com.test.util.ori_code;

import android.os.Bundle;

import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;
import com.test.util.custom_view2.Fragment_02;


public class OriCodeActivity extends MyBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test2;
    }

    private Class<?>[] fragmentArr ={Fragment_01.class, Fragment_02.class, };
    private String[] tabNames = {"Handler1", "Handler2",};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout_1);
        tab_layout.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        FragmentHelper fragmentHelper = new FragmentHelper(fm, fragmentArr, R.id.flt_content);
        tab_layout.setOnSelectChangedListener(fragmentHelper::onSwitchFragment);
        tab_layout.setCurrentPosition(0);
    }
}
