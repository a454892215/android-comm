package com.common;

import android.app.Application;

import com.common.utils.LogUtil;
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


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.LogEnable(true);
        initX5WebView();
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
