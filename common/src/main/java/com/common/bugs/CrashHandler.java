package com.common.bugs;

import android.content.Context;

import com.common.utils.LogUtil;
import com.common.utils.StringUtil;

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
            LocalBugHelper.appendTextToBugsFile(context, throwableInfo);
            LogUtil.e(throwableInfo);
            Thread.sleep(1000);
        } catch (Exception ex) {
            LogUtil.e(ex);
        }
    }
}
