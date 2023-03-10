package com.common.x5_web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
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
            webSetting.setGeolocationEnabled(true);//????????????
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
                data.putBoolean("standardFullScreen", false);//true?????????????????????false??????X5????????????????????????false???
                data.putBoolean("supportLiteWnd", true); //false??????????????????true?????????????????????????????????true???
                data.putInt("DefaultVideoScreen", 1); //1??????????????????????????????2?????????????????????????????????????????????1
                extension.invokeMiscMethod("setVideoParams", data);
                LogUtil.d("=================== X5WebView extension ????????????");
            } else {
                LogUtil.e("=================== X5WebView extension ??? null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("????????????==============" + e);
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
                    LogUtil.e("????????????????????????");
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


    private OnTouchDown onTouchDownListener;

    public void setOnTouchDownListener(OnTouchDown onTouchDownListener) {
        this.onTouchDownListener = onTouchDownListener;
    }

    public interface OnTouchDown {
        void onTouchDown();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (onTouchDownListener != null) {
                onTouchDownListener.onTouchDown();
                requestFocus();
            }
        }
        return super.dispatchTouchEvent(ev);

    }
}
