package com.common;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.common.bugs.CrashHandler;
import com.common.comm.L;
import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;
import com.common.x5_web.MyPreInitCallback;
import com.common.x5_web.MyTbsListener;
import com.common.x5_web.MyTbsLogClient;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;
import com.youth.banner.Banner;

import org.litepal.LitePal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class CommApp extends Application {

    protected boolean isInitX5Web = false;
    public static Application app;
//    private static HotFixCallback hotFixCallback;


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            LitePal.initialize(this);
            LogUtil.d("=======Application===初始化=======ProcessName:" + getProcessName(this));
            CrashHandler.init();
            if (SystemUtils.isMainProcess(this)) {
                app = this;
                CrashReport.initCrashReport(getApplicationContext(), "89a3be5c8c", BuildConfig.DEBUG);
                setWebViewPath(this);
              //  if (isInitX5Web) initX5WebView();
                LogUtil.d("==========是否初始化X5Web:" + isInitX5Web);
                L.init(this);
        /*        hotFixCallback = new HotFixCallback();
                hotFixCallback.init(this);
                registerActivityLifecycleCallbacks(hotFixCallback);*/
                LogUtil.d("=======主进程初始化完毕======");
            } else {
                LogUtil.d("=======其他进程初始化完毕======");
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private void initX5WebView() {

        HashMap<String, Object> map = new HashMap<>();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);

        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setCrashHandleCallback(new CrashReport.CrashHandleCallback() {
            public Map<String, String> onCrashHandleStart(
                    int crashType,
                    String errorType,
                    String errorMessage,
                    String errorStack) {

                LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
                String x5CrashInfo = com.tencent.smtt.sdk.WebView.getCrashExtraMessage(getApplicationContext());
                map.put("x5crashInfo", x5CrashInfo);
                return map;
            }

            @Override
            public byte[] onCrashHandleStart2GetExtraDatas(
                    int crashType,
                    String errorType,
                    String errorMessage,
                    String errorStack) {
                try {
                    return "Extra data.".getBytes("UTF-8");
                } catch (Exception e) {
                    return null;
                }
            }

        });
        try {
            CrashReport.initCrashReport(this, getPackageName(), true, strategy);

            QbSdk.setTbsLogClient(new MyTbsLogClient(this));
            QbSdk.setDownloadWithoutWifi(true);
            QbSdk.setTbsListener(new MyTbsListener());
            QbSdk.initX5Environment(getApplicationContext(), new MyPreInitCallback());
        } catch (Throwable e) {
           LogUtil.e(e);
        }

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

/*    public static HotFixCallback getHotFixCallback() {
        return null;
    }*/


}
