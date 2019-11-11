package com.example.jpushdemo;

import com.common.utils.LogUtil;


class Logger {

    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;

    static void i(String msg) {
        if (LOG_ENABLE) {
            LogUtil.i(msg);
        }
    }

    static void v(String msg) {
        if (LOG_ENABLE) {
            LogUtil.v(msg);
        }
    }

    static void d(String msg) {
        if (LOG_ENABLE) {
            LogUtil.d(msg);
        }
    }

    static void w(String msg) {
        if (LOG_ENABLE) {
            LogUtil.w(msg);
        }
    }

    static void e(String msg) {
        if (LOG_ENABLE) {
            LogUtil.e(msg);
        }
    }

}
