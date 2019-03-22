package com.test.util;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.common.base.BaseActivity;
import com.common.utils.ViewUtil;
import com.common.widget.CommonTabLayout;

/**
 * RecyclerView
 */
public class ConstraintLayoutTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonTabLayout tab_layout = findViewById(R.id.rg_body_left);
        FrameLayout flt_content = findViewById(R.id.flt_content);
        ViewUtil.onlyShowOneChildView(flt_content, 0);
        tab_layout.setOnSelectChangedListener(position -> ViewUtil.onlyShowOneChildView(flt_content, position));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint_layout_test;
    }
}
