package com.test.util;

import android.os.Bundle;

import com.common.base.BaseActivity;

public class XposedTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xpose_test;
    }
}
