package com.util;

/**
 * Author: Pan
 * 2019/9/16
 * Description:
 */
public class LogUtil {

    public static void d(String text) {
        System.out.println(text + getLineNum());
    }

    public static void e(String text) {
        System.err.println(text);
    }

    private static String getLineNum() {
        StackTraceElement ste = new Throwable().getStackTrace()[2];

        return ste.getClassName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ") ";
    }
}
