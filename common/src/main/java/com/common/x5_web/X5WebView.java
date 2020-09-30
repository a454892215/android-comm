package com.common.x5_web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.x5_web.entity.SearchRecordEntity;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;

public class X5WebView extends WebView {

    public X5WebView(Context context) {
        super(context);
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private Activity activity;

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebViewSettings(Activity activity, WebViewInfoCallBack webViewInfoCallBack) {
        try {
            this.activity = activity;
            setWebViewClient(new MyWebViewClient(activity, webViewInfoCallBack));
            setWebChromeClient(new MyWebChromeClient(webViewInfoCallBack));
            setDownloadListener(new MyDownloadListener(activity));
            WebSettings webSetting = this.getSettings();
            webSetting.setJavaScriptEnabled(true);
            webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
            webSetting.setAllowFileAccess(true);
            webSetting.setSupportZoom(true);
            webSetting.setBuiltInZoomControls(true);
            webSetting.setUseWideViewPort(true);
            webSetting.setSupportMultipleWindows(true);

            webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            webSetting.setSavePassword(true);
            webSetting.setDisplayZoomControls(false);
            webSetting.setAppCachePath(getContext().getApplicationContext().getCacheDir().getAbsolutePath());
            webSetting.setAllowContentAccess(true);
            webSetting.setAllowFileAccessFromFileURLs(true);
            webSetting.setAllowUniversalAccessFromFileURLs(true);

            webSetting.setLoadWithOverviewMode(true);
            webSetting.setAppCacheEnabled(true);
            webSetting.setDatabaseEnabled(true);
            webSetting.setDomStorageEnabled(true);
            webSetting.setGeolocationEnabled(true);//地理地位
            webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
            webSetting.setPluginsEnabled(true);
            webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
            webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
            IX5WebSettingsExtension settingsExtension = getSettingsExtension();
            if (settingsExtension != null) {
                settingsExtension.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
                settingsExtension.setDayOrNight(true);
                settingsExtension.setReadModeWebView(true);
            }


            IX5WebViewExtension extension = getX5WebViewExtension();
            if (extension != null) {
                Bundle data = new Bundle();
                data.putBoolean("standardFullScreen", false);//true表示标准全屏，false表示X5全屏；不设置默认false，
                data.putBoolean("supportLiteWnd", true); //false：关闭小窗；true：开启小窗；不设置默认true，
                data.putInt("DefaultVideoScreen", 1); //1：以页面内开始播放，2：以全屏开始播放；不设置默认：1
                extension.invokeMiscMethod("setVideoParams", data);
                LogUtil.d("=================== X5WebView extension 加载成功");
            } else {
                LogUtil.e("=================== X5WebView extension 是 null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("发生异常==============" + e);
        }
    }

    public boolean onWebBack() {
        if (canGoBack()) {
            getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            goBack();
            return true;
        } else {
            activity.finish();
        }
        return false;
    }

    public void goUrl(String url, Activity activity) {
        if (!TextUtils.isEmpty(url) && !url.startsWith("http:") && !url.startsWith("https:") && url.contains(".")) {
            url = "http:" + url;
        }
        if (!TextUtils.isEmpty(url) && url.startsWith("http:") || url.startsWith("https:")) {
            SystemUtils.hideSoftKeyboard(activity);
            loadUrl(url);
            requestFocus();
            postDelayed(() -> {
                SearchRecordEntity entity = new SearchRecordEntity();
                entity.setTitle(getTitle());
                entity.setUrl(getUrl());
                entity.setTime(System.currentTimeMillis());
                boolean save = entity.save();
                if (!save) {
                    LogUtil.e("保存搜索记录失败");
                }
            }, 700);

        }
    }

    public void destroy() {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }
        stopLoading();
        getSettings().setJavaScriptEnabled(false);
        clearHistory();
        clearView();
        removeAllViews();
    }

}
