package com.hook;

import com.common.utils.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

import de.robv.android.xposed.XposedBridge;

/**
 * Author: Pan
 * 2021/3/2
 * Description:
 */
class XLogUtil {

    private static final String TAG = "LLpp: ";

    public static void d(String msg) {
        XposedBridge.log(TAG + getLineNum() + "  text:  " + msg);
    }

    public static void d(Throwable e) {
        XposedBridge.log(TAG + getLineNum() + "  text:  " + getThrowableInfo(e));
    }


    private static String getLineNum() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return "(" + ste.getFileName() + ":" + ste.getLineNumber() + ") " + ste.getMethodName() + "()";
    }

    public static String getThrowableInfo(Throwable e) {
        String text = ":null:";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            text = sw.toString();
            sw.close();
        } catch (Exception e1) {
            LogUtil.e("===============e:" + e);
            e1.printStackTrace();
        }

        return text;
    }

}
