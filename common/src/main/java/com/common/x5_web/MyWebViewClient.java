package com.common.x5_web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.common.x5_web.entity.BookmarkEntity;
import com.common.x5_web.entity.HistoryRecordEntity;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    MyWebViewClient(BaseActivity activity, WebViewInfoCallBack webViewInfoCallBack) {
        this.activity = activity;
        this.webViewInfoCallBack = webViewInfoCallBack;
    }

    private BaseActivity activity;
    private WebViewInfoCallBack webViewInfoCallBack;

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        //  LogUtil.d("===========shouldOverrideUrlLoading=============url:" + url);
        if (url == null) return false;
        if (webViewInfoCallBack != null) {
            webViewInfoCallBack.onUrlChange(url);
        }
        try {
            if (!url.startsWith("http:") && !url.startsWith("https:")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                activity.startActivity(intent);
                return true;
            }
        } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }
        HistoryRecordEntity entity = new HistoryRecordEntity();
        entity.setTime(System.currentTimeMillis());
        entity.setUrl(url);
        entity.setTitle(view.getTitle());
        boolean save = entity.save();
      //  LogUtil.d("================保存历史记录是否成功：" + save + " url:" + url);
        view.loadUrl(url);//防止加载网页时调起系统浏览器
        return true;
    }

    @Override
    public void onFormResubmission(WebView webView, Message message, Message message1) {
        super.onFormResubmission(webView, message, message1);
        LogUtil.d("===========onFormResubmission=============");
    }

    @Override
    public void onDetectedBlankScreen(String s, int i) {
        super.onDetectedBlankScreen(s, i);
        LogUtil.d("===========onDetectedBlankScreen=============");
    }

    @Override
    public void onLoadResource(WebView webView, String s) {
        super.onLoadResource(webView, s);
        //  LogUtil.d("===========onLoadResource=============:" + s);
    }

    @Override
    public void onPageStarted(WebView webView, String url, Bitmap bitmap) {
        super.onPageStarted(webView, url, bitmap);
        LogUtil.d("===========onPageStarted=============url:" + url);
    }

    @Override
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        super.onReceivedClientCertRequest(webView, clientCertRequest);
        LogUtil.d("===========onReceivedClientCertRequest=============");
    }

    @Override
    public void onReceivedError(WebView webView, int i, String s, String s1) {
        super.onReceivedError(webView, i, s, s1);
        LogUtil.d("===========onReceivedError=============");
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String s, String s1) {
        super.onReceivedHttpAuthRequest(webView, httpAuthHandler, s, s1);
        LogUtil.d("===========onReceivedHttpAuthRequest=============");
    }

    @Override
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        LogUtil.d("===========onReceivedSslError=============");
    }

    @Override
    public void onTooManyRedirects(WebView webView, Message message, Message message1) {
        super.onTooManyRedirects(webView, message, message1);
        LogUtil.d("===========onTooManyRedirects=============" + message);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
        //  LogUtil.d("===========shouldInterceptRequest=============" + s);
        return super.shouldInterceptRequest(webView, s);
    }

}
