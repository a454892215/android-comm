package com.test.util;

import android.os.Bundle;
import com.common.base.BaseActivity;
import com.common.widget.TabLayout;

/**
 * RecyclerView
 */
public class ConstraintLayoutTestActivity extends BaseActivity {
    String[] names = {"ConstraintLayout线性和相对布局"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setData(names);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint_layout_test;
    }
}
