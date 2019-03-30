package com.test.util;

import android.os.Bundle;
import android.widget.Button;

import com.common.comm.MultiClick;
import com.common.utils.ToastUtil;
import com.test.util.base.MyBaseActivity;

public class ProcessLiveTestActivity extends MyBaseActivity {
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
