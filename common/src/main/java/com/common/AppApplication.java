package com.common;

import android.app.Application;

import com.common.x5_web.MyPreInitCallback;
import com.common.x5_web.MyTbsListener;
import com.common.x5_web.MyTbsLogClient;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class AppApplication extends Application {

   /* public AppApplication(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }*/

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
        initX5WebView();
        registerActivityLifecycleCallbacks(new ActivityCallbacks());
        LitePal.initialize(this);
        //  sTypeface = Typeface.createFromAsset(getAssets(), "fonts/DroidSansFallback.ttf");
    }

    private void initX5WebView() {
        QbSdk.setTbsLogClient(new MyTbsLogClient(this));
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(new MyTbsListener());
        QbSdk.initX5Environment(getApplicationContext(), new MyPreInitCallback());
    }

    private void initLogger() {
        PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
        builder.showThreadInfo(false).methodCount(2).methodOffset(1);
        PrettyFormatStrategy build = builder.build();
        Logger.addLogAdapter(new AndroidLogAdapter(build));
    }

}
