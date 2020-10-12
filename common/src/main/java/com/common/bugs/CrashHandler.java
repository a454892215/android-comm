package com.common.bugs;

import android.content.Context;

import com.common.utils.LogUtil;
import com.common.utils.StringUtil;

import java.util.concurrent.Executors;

/**
 * 如果有 bugly 需要在bugly 之前初始化 以避免覆盖需要bugly
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private CrashHandler(Context context) {
        this.context = context;
    }

    public static void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(new CrashHandler(context));
    }

    private Context context;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            String throwableInfo = StringUtil.getThrowableInfo(e);
            Executors.newSingleThreadExecutor().execute(() -> {
                LogUtil.d("捕获未处理异常： 线程名" + t.getName() + " 线程Id:" + t.getId()
                        + " 进程ID：" + android.os.Process.myPid() + " uid:" + android.os.Process.myUid());
                LogUtil.e(throwableInfo);
            });
            LocalBugHelper.appendTextToBugsFile(context, throwableInfo);
            Thread.sleep(5000);
        } catch (Exception ex) {
            LogUtil.e(ex);
        }
    }
}
