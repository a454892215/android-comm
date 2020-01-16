package com.common.hotfix;

import android.app.Activity;
import android.app.Application;

public interface BaseHotFix {

    void onAppCreate(Application application);

    void onActivityCreate(Activity activity);

    void onActivityResume(Activity activity);

    void onActivityPause(Activity activity);

    void onActivityDestroy(Activity activity);

}
