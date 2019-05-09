package com.test.util.x5web;

import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.common.widget.CommonEditText;
import com.common.widget.float_window.MultiViewFloatLayout;
import com.common.x5_web.WebViewInfoCallBack;
import com.common.x5_web.X5WebView;
import com.common.x5_web.dialog.MenuDialogFragment;
import com.common.x5_web.dialog.SearchRecordPop;
import com.tencent.smtt.sdk.WebView;
import com.test.util.R;

public class WebViewWindow implements View.OnClickListener {
    private final FragmentManager fm;
    private X5WebView web_view;
    private CommonEditText et_url_info;
    private ProgressBar progress_bar;

    private String home_url = "https://hao.360.cn";
    private SearchRecordPop searchRecordPop;
    private View rootView;
    private BaseActivity activity;
    private MultiViewFloatLayout multi_view_float;

    WebViewWindow(View itemView, BaseActivity activity, MultiViewFloatLayout multi_view_float) {
        rootView = itemView;
        this.multi_view_float = multi_view_float;
        this.activity = activity;
        fm = activity.getSupportFragmentManager();
        web_view = findViewById(R.id.web_view);
        web_view.initWebViewSettings(activity, new MyWebViewInfoCallBack());
        searchRecordPop = new SearchRecordPop(activity);
        searchRecordPop.setOnClickListener(url -> web_view.goUrl(url[0], activity));
        web_view.loadUrl(home_url);
        //web_view.requestFocus();
        setBrowserHeader();
        setBrowserFooter();
        activity.addOnBackPressedListener(() -> web_view.onWebBack());
    }

    private void setBrowserHeader() {
        et_url_info = findViewById(R.id.et);
        progress_bar = findViewById(R.id.progress_bar);
        View iv_url_clear = findViewById(R.id.iv_url_clear);
        iv_url_clear.setOnClickListener(v -> et_url_info.setText(""));
        findViewById(R.id.iv_search).setOnClickListener(v -> {
            Editable text = et_url_info.getText();
            if (text != null) {
                web_view.goUrl(text.toString(), activity);
            }
            if (searchRecordPop.isShowing()) searchRecordPop.dismiss();
        });
        et_url_info.setOnFocusChangeListener((v, hasFocus) -> {
            String url = web_view.getUrl();
            et_url_info.setText(url);
            if (hasFocus) {
                iv_url_clear.setVisibility(View.VISIBLE);
                searchRecordPop.showAsDropDown(et_url_info);
            } else {
                String title = web_view.getTitle();
                if (!TextUtils.isEmpty(title)) et_url_info.setText(title);
                iv_url_clear.setVisibility(View.INVISIBLE);
            }
        });
        et_url_info.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Editable text = et_url_info.getText();
                if (text != null) {
                    web_view.goUrl(text.toString(), activity);
                }
                if (searchRecordPop.isShowing()) searchRecordPop.dismiss();
                return true;
            }
            return false;
        });
    }

    private void setBrowserFooter() {
        findViewById(R.id.tv_go_home).setOnClickListener(this);
        findViewById(R.id.tv_refresh).setOnClickListener(this);
        findViewById(R.id.tv_go_forward).setOnClickListener(this);
        findViewById(R.id.tv_go_back).setOnClickListener(this);
        findViewById(R.id.tv_menu).setOnClickListener(this);
        findViewById(R.id.tv_window).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_go_home:
                web_view.loadUrl(home_url);
                break;
            case R.id.tv_refresh:
                web_view.reload();
                break;
            case R.id.tv_go_forward:
                if (web_view.canGoForward()) {
                    web_view.goForward();
                }
                break;
            case R.id.tv_go_back:
                web_view.onWebBack();
                break;
            case R.id.tv_menu:
                MenuDialogFragment menuDialog = new MenuDialogFragment();
                menuDialog.setWebView(web_view);
                menuDialog.show(fm, menuDialog.getClass().getName());
                break;
            case R.id.tv_window:
                multi_view_float.switchWindowMode(null);
                break;

        }
    }

    private class MyWebViewInfoCallBack extends WebViewInfoCallBack {
        @Override
        public void onReceivedTitle(String title) {
            super.onReceivedTitle(title);
            et_url_info.setText(title);
        }

        @Override
        public void onProgressChanged(int progress) {
            super.onProgressChanged(progress);
            progress_bar.setProgress(progress);
            if (progress == 100) {
                progress_bar.setVisibility(View.INVISIBLE);
            } else {
                progress_bar.setVisibility(View.VISIBLE);
            }
            LogUtil.d("=============progress:" + progress);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            View item_view = LayoutInflater.from(activity).inflate(R.layout.layout_x5web_item, multi_view_float, false);
            multi_view_float.addView(item_view);
            X5WebView web_view = item_view.findViewById(R.id.web_view);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(web_view);
            web_view.setWebChromeClient(view.getWebChromeClient());
            resultMsg.sendToTarget();
            new WebViewWindow(item_view, activity, multi_view_float);
            return true;
        }
    }

    public <T extends View> T findViewById(int id) {
        return rootView.findViewById(id);
    }

}
