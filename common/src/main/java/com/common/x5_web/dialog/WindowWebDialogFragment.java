package com.common.x5_web.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.common.R;
import com.common.base.BaseDialogFragment;
import com.common.utils.LogUtil;
import com.common.x5_web.X5WebView;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class WindowWebDialogFragment extends BaseDialogFragment {

    private X5WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_dialog_window_web;
    }

    @Override
    protected void initView() {
        try {
            FrameLayout flt_content = findViewById(R.id.flt_content);
            // agentWeb.getWebCreator().getWebView();
            webView = null;
            transport.setWebView(webView);
        } catch (Exception e) {
            LogUtil.e("发生异常：" + e);
            e.printStackTrace();
        }
    }

    private WebChromeClient client;
    private WebView.WebViewTransport transport;

    public void setWebChromeClient(WebChromeClient client) {
        this.client = client;
    }

    public void setWebViewTransport(WebView.WebViewTransport transport) {
        this.transport = transport;
    }

    public void onPause() {
        super.onPause();
        if (webView != null) webView.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
