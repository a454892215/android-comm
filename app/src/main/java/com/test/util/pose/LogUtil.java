package com.test.util.pose;

import de.robv.android.xposed.XposedBridge;

public class LogUtil {
    private static final boolean debug = true;
    private static final String TAG = "LLpp: ";

    public static void d(String message) {
        if (debug) {
            XposedBridge.log(TAG + message);
        }
    }

    public static void e(String message) {
        XposedBridge.log(TAG + message);
    }
}
