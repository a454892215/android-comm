package com.common.x5_web;

import android.net.Uri;
import android.view.View;

import com.common.utils.LogUtil;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebStorage;
import com.tencent.smtt.sdk.WebView;

public class MyWebChromeClient extends WebChromeClient {
    @Override
    public boolean onJsTimeout() {
        LogUtil.d("==============onJsTimeout============");
        return super.onJsTimeout();
    }

    @Override
    public void onProgressChanged(WebView webView, int i) {
        super.onProgressChanged(webView, i);
     //   LogUtil.d("==============onProgressChanged============");
    }

    @Override
    public boolean onJsAlert(WebView webView, String s, String s1, JsResult jsResult) {
        LogUtil.d("==============onJsAlert============");
        return super.onJsAlert(webView, s, s1, jsResult);
    }

    @Override
    public boolean onJsPrompt(WebView webView, String s, String s1, String s2, JsPromptResult jsPromptResult) {
        LogUtil.d("==============onJsPrompt============");
        return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        LogUtil.d("==============onShowFileChooser============");
        return super.onShowFileChooser(webView, valueCallback, fileChooserParams);
    }

    @Override
    public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
        LogUtil.d("==============onShowCustomView============");
        super.onShowCustomView(view, customViewCallback);
    }

    @Override
    public void onReachedMaxAppCacheSize(long l, long l1, WebStorage.QuotaUpdater quotaUpdater) {
        super.onReachedMaxAppCacheSize(l, l1, quotaUpdater);
        LogUtil.d("==============onReachedMaxAppCacheSize============");
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissionsCallback callback) {
        callback.invoke(origin, true, false);
        super.onGeolocationPermissionsShowPrompt(origin, callback);
        LogUtil.d("==============onGeolocationPermissionsShowPrompt============");
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
        LogUtil.d("==============onGeolocationPermissionsHidePrompt============");
    }
}
