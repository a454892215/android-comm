package com.common.bugs;

import android.os.Handler;
import android.os.Looper;

import com.common.CommApp;
import com.common.utils.LogUtil;
import com.common.utils.StringUtil;

import java.util.concurrent.Executors;

/**
 * 如果有 bugly 需要在bugly 之前初始化 以避免覆盖需要bugly
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private CrashHandler() {
    }

    public static void init() {
        //处理子线程未捕获异常
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler());
        //处理主线程未捕获异常
        new Handler(Looper.getMainLooper()).post(() -> {
            //主线程异常拦截
            while (true) {
                try {
                    Looper.loop();//主线程的异常会从这里抛出
                } catch (Throwable e) {
                    onException(Thread.currentThread(), e);
                }
            }
        });
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            onException(t, e);
            Thread.sleep(5000);
        } catch (Exception ex) {
            LogUtil.e(ex);
        }
    }

    private static void onException(Thread t, Throwable e) {
        String throwableInfo = StringUtil.getThrowableInfo(e);
        Executors.newSingleThreadExecutor().execute(() -> {
            LogUtil.e("捕获未处理异常： 线程名:" + t.getName() + " 线程Id:" + t.getId()
                    + " 进程ID：" + android.os.Process.myPid() + " uid:" + android.os.Process.myUid());
            LogUtil.e(throwableInfo);
        });
        LocalBugHelper.appendTextToBugsFile(CommApp.app, throwableInfo);
    }
}
