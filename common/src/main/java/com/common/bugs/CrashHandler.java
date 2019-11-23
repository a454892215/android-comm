package com.common.bugs;

import android.content.Context;

import com.common.utils.LogUtil;
import com.common.utils.StringUtil;

import java.util.concurrent.Executors;

/**
 * 如果有 bugly 需要在bugly 之前初始化 以避免覆盖需要bugly
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public CrashHandler(Context context) {
        this.context = context;
    }

    private Context context;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        try {
            String throwableInfo = StringUtil.getThrowableInfo(e);
            Executors.newSingleThreadExecutor().execute(()-> LogUtil.e(throwableInfo));
            LocalBugHelper.appendTextToBugsFile(context, throwableInfo);
            Thread.sleep(5000);
        } catch (Exception ex) {
            LogUtil.e(ex);
        }
    }
}
