package com.test.util.x5web;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;

import com.common.utils.StatusBarUtil;
import com.common.widget.float_window.MultiViewFloatLayout;
import com.common.x5_web.X5WebView;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

public class X5WebTestActivity extends MyBaseActivity {


    private MultiViewFloatLayout multi_view_float;

    public List<WebViewWindow> getWindowList() {
        return WindowList;
    }

    private List<WebViewWindow> WindowList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//网页中的视频，上屏幕的时候，可能出现闪烁的情况
        StatusBarUtil.setStatusBarBgColor(this, Color.parseColor("#cccccc"));
        setTitle("X5WebView测试");

        multi_view_float = findViewById(R.id.multi_view);
        WebViewWindow webViewWindow = new WebViewWindow(multi_view_float.getChildAt(0), this, multi_view_float);
        WindowList.add(webViewWindow);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_x5_web_test;
    }

    @Override
    protected void onDestroy() {
        int childCount = multi_view_float.getChildCount();
        for (int i = 0; i < childCount; i++) {
            X5WebView web_view = multi_view_float.getChildAt(i).findViewById(R.id.web_view);
            web_view.destroy();
        }
        super.onDestroy();
        // android.os.Process.killProcess(android.os.Process.myPid());

    }


}
