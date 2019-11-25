package com.test.util;

import android.os.Bundle;

import com.test.util.base.BaseAppActivity;

public class JRTTTestActivity extends BaseAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getText() {
        return BuildConfig.app_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jrtt;
    }
}
