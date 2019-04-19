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
import com.tencent.smtt.sdk.WebView;

public class MyWebChromeClient extends WebChromeClient {
    MyWebChromeClient(WebViewInfoCallBack webViewInfoCallBack) {
        this.webViewInfoCallBack = webViewInfoCallBack;
    }

    private WebViewInfoCallBack webViewInfoCallBack;

    @Override
    public boolean onJsTimeout() {
        LogUtil.d("==============onJsTimeout============");
        return super.onJsTimeout();
    }


    @Override
    public void onProgressChanged(WebView webView, int progress) {
        super.onProgressChanged(webView, progress);
        if (webViewInfoCallBack != null) webViewInfoCallBack.onProgressChanged(progress);
      /*  if (progress < 70) {
            if (webViewInfoCallBack != null) webViewInfoCallBack.onProgressChanged(progress);
        } else {
            if (webViewInfoCallBack != null) {
                MyCountDownTimer myCountDownTimer = new MyCountDownTimer(10, 25);
                myCountDownTimer.setOnTickListener((time, count) -> webViewInfoCallBack.onProgressChanged(70 + 30 * (count / 10)));
                myCountDownTimer.start();
            }
        }*/

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

    @Override
    public void onReceivedTitle(WebView webView, String title) {
        super.onReceivedTitle(webView, title);
        if (webViewInfoCallBack != null) webViewInfoCallBack.onReceivedTitle(title);
    }

}
