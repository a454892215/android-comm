package com.test.util;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;

import com.common.widget.CommonEditText;
import com.common.x5_web.MyWebViewClient;
import com.common.x5_web.X5WebView;
import com.test.util.base.BaseAppActivity;

public class X5WebTestActivity extends BaseAppActivity {

    private X5WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//网页中的视频，上屏幕的时候，可能出现闪烁的情况
        setTitle("X5WebView测试");

        CommonEditText et = findViewById(R.id.et);
        findViewById(R.id.iv_search).setOnClickListener(v -> {
            String url = et.getText().toString();
            if (!TextUtils.isEmpty(url) && !url.startsWith("http:") && !url.startsWith("https:")) {
                url = "http:" + url;
            }
            if (!TextUtils.isEmpty(url)) {
                web_view.loadUrl(url);
            }

        });

        web_view = findViewById(R.id.web_view);
        web_view.initWebViewSettings(this, et::setText);
        web_view.loadUrl("http://baidu.com");
        web_view.requestFocus();
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
