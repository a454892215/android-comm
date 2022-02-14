package com.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.common.utils.LogUtil;
import com.common.utils.SystemUtils;


/**
 * Author: Pan
 * 2021/6/19
 * Description:
 */
public class AidlService3 extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("====AidlService==onCreate=========ProcessName:" + SystemUtils.getProcessName());
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d("======onBind=========");
        return binder;
    }

    private final IBinder binder = new MyBinder();

    private static class MyBinder extends IMyAidlInterface.Stub {


        @Override
        public void basicTypes(int aInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {

        }

        @Override
        public int getValue(int value) {
            return value + 1;
        }

        @Override
        public String getName(String value) {
            return value + "-22";
        }

        @Override
        public void test(String value) {
            LogUtil.d("========:" + value);
        }
    }
}
