package com.test.util;

import android.os.Bundle;
import android.widget.Button;

import com.common.base.BaseActivity;
import com.common.utils.ToastUtil;

public class ProgressLiveTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(v -> ToastUtil.showLong(activity, getText()));
    }

    public String getText() {
        return "进程保活";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress_live_test;
    }
}
