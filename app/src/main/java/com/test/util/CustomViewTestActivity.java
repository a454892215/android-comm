package com.test.util;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.widget.FrameLayout;

import com.common.helper.FragmentHelper;
import com.common.utils.LogUtil;
import com.common.utils.ViewUtil;
import com.common.widget.CommonTabLayout;
import com.test.util.base.MyBaseActivity;
import com.test.util.fragment.CityPickerFragment;
import com.test.util.fragment.DialogTestFragment;
import com.test.util.fragment.RVAnimFragment;

/**
 * RecyclerView
 */
public class CustomViewTestActivity extends MyBaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view_test;
    }
    Class[] fragmentArr = {RVAnimFragment.class, CityPickerFragment.class, DialogTestFragment.class};
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.rg_body_left);
        FrameLayout flt_content = findViewById(R.id.flt_content);
        ViewUtil.onlyShowOneChildView(flt_content, 0);
        fm = getSupportFragmentManager();
        tab_layout.setOnSelectChangedListener(position -> {
            LogUtil.d("===========setOnSelectChangedListener=============:" + position);
            FragmentHelper.onSwitchFragment(fm, fragmentArr, position, R.id.flt_content);
        });
        tab_layout.setCurrentPosition(0);
    }


}
