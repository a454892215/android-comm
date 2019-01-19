package com.test.util;

import android.os.Bundle;
import android.widget.Button;

import com.common.base.BaseActivity;
import com.common.utils.ToastUtil;

public class XposedTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> ToastUtil.showLong(activity, "我是button"));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xpose_test;
    }
}
