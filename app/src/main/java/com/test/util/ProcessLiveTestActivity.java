package com.test.util;

import android.os.Bundle;
import android.widget.Button;

import com.common.base.BaseActivity;
import com.common.comm.MultiClick;
import com.common.utils.ToastUtil;

public class ProcessLiveTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button btn = findViewById(R.id.btn);
        MultiClick multiClick = new MultiClick();
        btn.setOnClickListener(v -> {
            if (multiClick.isToFastClickCondition()) {
                ToastUtil.showLong(activity, getText());
            }
        });
    }

    public String getText() {
        return BuildConfig.app_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress_live_test;
    }
}
