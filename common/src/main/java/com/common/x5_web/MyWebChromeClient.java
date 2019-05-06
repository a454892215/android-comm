package com.common.x5_web;

import android.net.Uri;
import android.os.Message;
import android.view.View;

import com.common.utils.LogUtil;
import com.common.x5_web.dialog.WindowWebDialogFragment;
import com.common.x5_web.entity.HistoryRecordEntity;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

import org.litepal.LitePal;

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
        HistoryRecordEntity entity = new HistoryRecordEntity();
        entity.setTime(System.currentTimeMillis());
        entity.setUrl(webView.getUrl());
        entity.setTitle(title);
        boolean save = entity.save();
        if (!save) {
            LogUtil.e("保存数据到数据库失败");
        }
        int count = LitePal.count(HistoryRecordEntity.class);
        if (count > 5000) {
            for (int i = 0; i < count - 5000; i++) {
                HistoryRecordEntity first = LitePal.findFirst(HistoryRecordEntity.class);
                first.delete();
            }
        }
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WindowWebDialogFragment dialogFragment = new WindowWebDialogFragment();
        dialogFragment.setWebChromeClient(this);
        dialogFragment.setWebViewTransport((WebView.WebViewTransport) resultMsg.obj);
        view.postDelayed(resultMsg::sendToTarget, 200);
       // dialogFragment.show(view.getContext().getSupportFragmentManager(), dialogFragment.getClass().getName());
        return true;
    }
}
