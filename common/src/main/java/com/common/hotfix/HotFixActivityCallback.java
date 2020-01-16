package com.common.hotfix;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.common.utils.LogUtil;

import java.lang.ref.WeakReference;

public class HotFixActivityCallback implements Application.ActivityLifecycleCallbacks {
    private int foreAppCount;
    private WeakReference<Activity> weakReference;
    private HotFixHandler hotFixHandler = new HotFixHandler();
    private BaseHotFix baseHotFix;

    public void init(Application application) {
        hotFixHandler.init(application);
        baseHotFix = hotFixHandler.getBaseHotFix();
        baseHotFix.onAppCreate(application);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        weakReference = new WeakReference<>(activity);
        if (baseHotFix != null) baseHotFix.onActivityCreate(getCurrentActivity());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        foreAppCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (baseHotFix != null) baseHotFix.onActivityResume(getCurrentActivity());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        if (baseHotFix != null) baseHotFix.onActivityPause(getCurrentActivity());
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
        if (baseHotFix != null) baseHotFix.onActivityDestroy(getCurrentActivity());
    }

    private Activity getCurrentActivity() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }
}
