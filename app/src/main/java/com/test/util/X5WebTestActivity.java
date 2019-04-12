package com.test.util;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.common.x5_web.X5WebView;
import com.test.util.base.BaseAppActivity;

public class X5WebTestActivity extends BaseAppActivity {

    private X5WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSetLayoutId = false;
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//网页中的视频，上屏幕的时候，可能出现闪烁的情况
        setTitle("X5WebView测试");
        // setBrowserHeader();
        web_view = new X5WebView(this);
        web_view.initWebViewSettings(this, null);
        web_view.loadUrl("http://www.baidu.com");
        web_view.requestFocus();
        setContentView(web_view);
    }

  /*  private void setBrowserHeader() {
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
    }*/

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
        return -1;
    }

    @Override
    protected void onDestroy() {
        if (web_view != null) {
            ViewParent parent = web_view.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(web_view);
            }
            web_view.stopLoading();
            web_view.getSettings().setJavaScriptEnabled(false);
            web_view.clearHistory();
            web_view.clearView();
            web_view.removeAllViews();
            web_view.destroy();
            super.onDestroy();
            //  android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
