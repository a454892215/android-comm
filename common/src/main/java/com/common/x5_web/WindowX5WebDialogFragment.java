package com.common.x5_web;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.base.BaseDialogFragment;
import com.common.utils.LogUtil;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;

public class WindowX5WebDialogFragment extends BaseDialogFragment {


    private X5WebView x5_web_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_dialog_window_x5web;
    }

    @Override
    protected void initView() {
        try {
            x5_web_view = findViewById(R.id.x5_web_view);
            x5_web_view.initWebViewSettings(activity, null);
            x5_web_view.setWebChromeClient(client);
            transport.setWebView(x5_web_view);
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

    @Override
    public void onDestroy() {
        x5_web_view.destroy();
        super.onDestroy();
    }

}
