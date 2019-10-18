package com.common;

import android.app.Application;

import com.common.comm.L;
import com.common.utils.SharedPreUtils;
import com.common.x5_web.MyPreInitCallback;
import com.common.x5_web.MyTbsListener;
import com.common.x5_web.MyTbsLogClient;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class AppApplication extends Application {

    protected boolean isInitX5Web = false;

    @Override
    public void onCreate() {
        super.onCreate();
        if (isInitX5Web) initX5WebView();
        L.init(this);
        SharedPreUtils.initSp(getApplicationContext());
        registerActivityLifecycleCallbacks(new ActivityCallbacks());
        LitePal.initialize(this);
    }

    private void initX5WebView() {
        QbSdk.setTbsLogClient(new MyTbsLogClient(this));
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(new MyTbsListener());
        QbSdk.initX5Environment(getApplicationContext(), new MyPreInitCallback());
    }


}
