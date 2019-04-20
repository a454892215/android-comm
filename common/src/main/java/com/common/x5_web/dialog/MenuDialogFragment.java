package com.common.x5_web.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.WindowManager;

import com.common.R;
import com.common.base.BaseDialogFragment;
import com.common.x5_web.X5WebView;

public class MenuDialogFragment extends BaseDialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAnimStyle(R.style.dialog_anim_bottom).setGravity(Gravity.BOTTOM);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setDimeAmount(0.1f);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_web_menu;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_setting).setOnClickListener(v -> {

        });
        findViewById(R.id.tv_night_mode).setOnClickListener(v -> {

        });
        findViewById(R.id.tv_add_bookmark).setOnClickListener(v -> {

        });
        findViewById(R.id.tv_his).setOnClickListener(v -> {
            HisDialogFragment dialogFragment = new HisDialogFragment();
            dialogFragment.setOnClickListener(url -> {
                if (webView != null) webView.loadUrl(url[0]);
            });
            dialogFragment.show(fm, dialogFragment.getClass().getName());
        });

        findViewById(R.id.tv_bookmark).setOnClickListener(v -> {
            BookmarkDialogFragment dialogFragment = new BookmarkDialogFragment();
            dialogFragment.setOnClickListener(url -> {
                if (webView != null) webView.loadUrl(url[0]);
            });
            dialogFragment.show(fm, dialogFragment.getClass().getName());
        });
    }

    private X5WebView webView;

    public void setWebView(X5WebView webView) {
        this.webView = webView;
    }

}
