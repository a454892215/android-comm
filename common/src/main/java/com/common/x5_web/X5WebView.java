package com.common.x5_web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
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

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebViewSettings(BaseActivity activity, MyWebViewClient.OnUrlChangeListener onUrlChangeListner) {
        try {
            setWebViewClient(new MyWebViewClient(activity, onUrlChangeListner));
            setWebChromeClient(new MyWebChromeClient());
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
            webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
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
            } else {
                LogUtil.e("=================== X5WebView extension 是 null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.d("发生异常==============" + e);
        }
    }

}
