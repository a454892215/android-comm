package com.common;

import android.app.Application;

import com.common.utils.LogUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.tencent.smtt.sdk.QbSdk;

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
        //  sTypeface = Typeface.createFromAsset(getAssets(), "fonts/DroidSansFallback.ttf");
    }

    private void initX5WebView() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean isSuccess) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtil.d(" X5WebView 是否成功： " + isSuccess);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void initLogger() {
        PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
        builder.showThreadInfo(false).methodCount(2).methodOffset(1);
        PrettyFormatStrategy build = builder.build();
        Logger.addLogAdapter(new AndroidLogAdapter(build));
    }

}
