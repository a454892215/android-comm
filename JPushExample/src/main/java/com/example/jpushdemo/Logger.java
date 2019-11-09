package com.example.jpushdemo;

import android.util.Log;

/**
 * Created by efan on 2017/4/13.
 */

class Logger {

    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;

    static void i(String msg){
        if (LOG_ENABLE){
            Log.i("LLpp", msg);
        }
    }
    static void v(String msg){
        if (LOG_ENABLE){
            Log.v("LLpp", msg);
        }
    }
    static void d(String msg){
        if (LOG_ENABLE){
            Log.d("LLpp", msg);
        }
    }
    static void w(String msg){
        if (LOG_ENABLE){
            Log.w("LLpp", msg);
        }
    }
    static void e(String msg){
        if (LOG_ENABLE){
            Log.e("LLpp", msg);
        }
    }

}
