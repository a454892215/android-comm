package com.test.util;

import android.os.Bundle;

import com.tencent.bugly.crashreport.CrashReport;
import com.test.util.base.MyBaseActivity;

public class BuglyTestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_1).setOnClickListener(v -> CrashReport.testJavaCrash());
        findViewById(R.id.btn_2).setOnClickListener(v -> {
            int a = 0;
            int b = 0;
            int c = a/b;
        });
        findViewById(R.id.btn_3).setOnClickListener(v -> {
            BuglyTestActivity activity = null;
            activity.getLayoutId();
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
