package com.test.util;

import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ProgressBar;

import com.common.utils.LogUtil;
import com.common.widget.CommonEditText;
import com.common.x5_web.WebViewInfoCallBack;
import com.common.x5_web.X5WebView;
import com.common.x5_web.dialog.MenuDialogFragment;
import com.test.util.base.BaseAppActivity;

public class X5WebTestActivity extends BaseAppActivity {

    private X5WebView web_view;
    private CommonEditText et_url_info;
    private ProgressBar progress_bar;

    private String home_url = "https://www.hao123.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // isSetLayoutId = false;
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//网页中的视频，上屏幕的时候，可能出现闪烁的情况
        setTitle("X5WebView测试");
        // setBrowserHeader();
        // web_view = new X5WebView(this);
        setBrowserHeader();
        setBrowserFooter();
        web_view = findViewById(R.id.web_view);
        web_view.initWebViewSettings(this, new MyWebViewInfoCallBack());
        web_view.loadUrl(home_url);
        web_view.requestFocus();
        // setContentView(web_view);
    }

    private void setBrowserHeader() {
        et_url_info = findViewById(R.id.et);
        progress_bar = findViewById(R.id.progress_bar);
        findViewById(R.id.iv_search).setOnClickListener(v -> {
            String url = et_url_info.getText().toString();
            if (!TextUtils.isEmpty(url) && !url.startsWith("http:") && !url.startsWith("https:") && url.contains(".")) {
                url = "http:" + url;
            }
            if (!TextUtils.isEmpty(url) && url.startsWith("http:") || url.startsWith("https:")) {
                web_view.loadUrl(url);
            }

        });
        et_url_info.setSelectAllOnFocus(true);
    }

    private void setBrowserFooter() {
        findViewById(R.id.tv_go_home).setOnClickListener(this);
        findViewById(R.id.tv_refresh).setOnClickListener(this);
        findViewById(R.id.tv_go_forward).setOnClickListener(this);
        findViewById(R.id.tv_go_back).setOnClickListener(this);
        findViewById(R.id.tv_menu).setOnClickListener(this);
        // findViewById(R.id.tv_go_his).setOnClickListener(this);
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
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_go_home:
                web_view.loadUrl(home_url);
                break;
            case R.id.tv_refresh:
                web_view.reload();
                break;
            case R.id.tv_go_forward:
                if (web_view.canGoForward()) {
                    web_view.goForward();
                }
                break;
            case R.id.tv_go_back:
                if (web_view.canGoBack()) {
                    web_view.goBack();
                }
                break;
            case R.id.tv_menu:
                MenuDialogFragment menuDialog = new MenuDialogFragment();
                menuDialog.setWebView(web_view);
                menuDialog.show(fm, menuDialog.getClass().getName());
                break;

        }
    }

    private class MyWebViewInfoCallBack extends WebViewInfoCallBack {
        @Override
        public void onReceivedTitle(String title) {
            super.onReceivedTitle(title);
            et_url_info.setText(title);
        }

        @Override
        public void onProgressChanged(int progress) {
            super.onProgressChanged(progress);
            progress_bar.setProgress(progress);
            if (progress == 100) {
                progress_bar.setVisibility(View.INVISIBLE);
            } else {
                progress_bar.setVisibility(View.VISIBLE);
            }
            LogUtil.d("=============progress:" + progress);
        }
    }

}
