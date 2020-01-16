package com.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.common.bugs.CrashHandler;
import com.common.comm.L;
import com.common.hotfix.HotFixActivityCallback;
import com.common.utils.SystemUtils;
import com.common.x5_web.MyPreInitCallback;
import com.common.x5_web.MyTbsListener;
import com.common.x5_web.MyTbsLogClient;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import org.litepal.LitePal;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class CommApp extends Application {

    protected boolean isInitX5Web = false;
    public static Application app;


    @Override
    public void onCreate() {
        super.onCreate();
        if(SystemUtils.isMainProcess(this)){
            app = this;
            CrashHandler.init(this);
            CrashReport.initCrashReport(getApplicationContext(), "89a3be5c8c", BuildConfig.DEBUG);
            setWebViewPath(this);
            if (isInitX5Web) initX5WebView();
            L.init(this);
            HotFixActivityCallback activityCallbacks = new HotFixActivityCallback();
            activityCallbacks.init(this);
            registerActivityLifecycleCallbacks(activityCallbacks);
            LitePal.initialize(this);
        }
    }

    private void initX5WebView() {
        QbSdk.setTbsLogClient(new MyTbsLogClient(this));
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.setTbsListener(new MyTbsListener());
        QbSdk.initX5Environment(getApplicationContext(), new MyPreInitCallback());
    }


    public void setWebViewPath(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(context);
            String packageName = getApplicationContext().getPackageName();
            if (!packageName.equals(processName)) {//判断不等于默认进程名称
                WebView.setDataDirectorySuffix(processName);
            }
        }
    }

    public String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

}
