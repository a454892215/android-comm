package com.common.x5_web;

import com.common.utils.LogUtil;
import com.tencent.smtt.sdk.QbSdk;

public class MyPreInitCallback implements QbSdk.PreInitCallback {
    @Override
    public void onViewInitFinished(boolean isSuccess) {
        //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
        LogUtil.d(" X5WebView 是否成功： " + isSuccess);
    }

    @Override
    public void onCoreInitFinished() {
    }
}
