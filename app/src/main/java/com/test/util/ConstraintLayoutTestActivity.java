package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewRVAdapter;
import com.common.base.BaseActivity;
import com.common.base.BaseRVAdapter;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;

/**
 * RecyclerView
 */
public class ConstraintLayoutTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_constraint_layout_test;
    }
}
