package com.test.util;

import android.os.Bundle;
import android.widget.Button;

import com.common.utils.ToastUtil;
import com.test.util.base.BaseAppActivity;

public class XposedTestActivity extends BaseAppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> ToastUtil.showLong(getText()));
    }

    public String getText() {
        return "我没有被劫持gaga2";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xpose_test;
    }
}
