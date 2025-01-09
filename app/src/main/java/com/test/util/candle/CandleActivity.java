package com.test.util.candle;

import android.os.Bundle;
import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

public class CandleActivity extends MyBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.aty_candle_layout;
    }

    Class<?>[] fragmentArr = {CoinFragment01.class, CoinFragment02.class, CoinFragment03.class };
    String[] tabNames = {"Tab1", "Tab2", "Tab3" };

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
