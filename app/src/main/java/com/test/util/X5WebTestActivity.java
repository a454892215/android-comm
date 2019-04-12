package com.test.util;

import android.os.Bundle;

import com.common.widget.X5WebView;
import com.test.util.base.BaseAppActivity;

public class X5WebTestActivity extends BaseAppActivity {

    private X5WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("X5WebView测试");
        web_view = findViewById(R.id.web_view);
        web_view.loadUrl("https://www.baidu.com");
    }

    @Override
    public void onBackPressed() {
        if (web_view != null && web_view.canGoBack()) {
            web_view.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_x5_web_test;
    }
}
