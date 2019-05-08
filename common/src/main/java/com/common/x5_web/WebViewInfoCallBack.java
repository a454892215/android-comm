package com.common.x5_web;

import android.os.Message;

import com.tencent.smtt.sdk.WebView;

@SuppressWarnings("unused")
public abstract class WebViewInfoCallBack {

    public void onUrlChange(String url) {
    }

    public void onReceivedTitle(String title) {
    }

    public void onProgressChanged(int progress) {
    }

    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return true;
    }
}
