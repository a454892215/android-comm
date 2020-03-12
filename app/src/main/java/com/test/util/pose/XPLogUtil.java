package com.test.util.pose;

import android.util.Log;

import com.common.utils.StringUtil;

import de.robv.android.xposed.XposedBridge;

public class XPLogUtil {

    private static final String TAG = "LLpp: ";

    public static void i(String msg) {
        if (msg != null) {
            int length = msg.length();
            int count = 1024 * 3;
            int times = length / count + 1;
            for (int i = 0; i < times; i++) {
                int end = (i + 1) * count > msg.length() ? msg.length() : (i + 1) * count;
                XposedBridge.log(TAG + getLineNum() + "  text:  " + msg.substring(i * count, end));
            }
        }
    }

    public static void e(Throwable e) {
        Log.e(TAG + getLineNum(), StringUtil.getThrowableInfo(e));
    }


    private static String getLineNum() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return "(" + ste.getFileName() + ":" + ste.getLineNumber() + ") " + ste.getMethodName() + "()";
    }
}
