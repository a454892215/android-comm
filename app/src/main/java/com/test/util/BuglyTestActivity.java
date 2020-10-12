package com.test.util;

import android.os.Bundle;
import android.view.View;

import com.tencent.bugly.crashreport.CrashReport;
import com.test.util.base.MyBaseActivity;

import java.util.concurrent.Executors;

public class BuglyTestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_java_main_thread_e_1).setOnClickListener(this);
        findViewById(R.id.btn_java_io_thread_e_1).setOnClickListener(this);
        findViewById(R.id.btn_main_to_c_e).setOnClickListener(this);
        findViewById(R.id.btn_io_to_c_e).setOnClickListener(this);
        findViewById(R.id.btn_bugly_test).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_java_main_thread_e_1:
                throw new RuntimeException("java层 主线程 自定义RuntimeException异常");
            case R.id.btn_java_io_thread_e_1:
                Executors.newSingleThreadExecutor().execute(() -> {
                    throw new RuntimeException("java层 子线程 自定义RuntimeException异常");
                });
                break;
            case R.id.btn_main_to_c_e:
                throw new RuntimeException("java主线程  到C代码 异常");
            case R.id.btn_io_to_c_e:
                throw new RuntimeException("java子线程  到C代码 异常");
            case R.id.btn_bugly_test:
                CrashReport.testJavaCrash();
        }
    }

    public String getText() {
        return BuildConfig.app_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exception_test;
    }
}
