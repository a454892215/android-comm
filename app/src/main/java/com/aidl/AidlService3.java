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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("======onStartCommand=======");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("======onDestroy=======");
    }

    private final IBinder binder = new MyBinder();
}
