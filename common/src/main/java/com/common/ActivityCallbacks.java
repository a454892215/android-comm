package com.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.common.utils.LogUtil;

public class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {
    private int foreAppCount;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        LogUtil.d("====ActivityCallbacks========onActivityStarted======app进入前台========:" + activity.getClass().getSimpleName());
        foreAppCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        foreAppCount--;
        if (foreAppCount == 0) {
            LogUtil.d("======ActivityCallbacks======已经进入后台==============:");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
