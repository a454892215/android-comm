package com.common.hotfix;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public interface BaseHotFix {

    void onAppCreate(Application application);

    void onActivityCreate(Activity activity);

    void onActivityResume(Activity activity);

    void onActivityPause(Activity activity);

    void onActivityDestroy(Activity activity);

    void onSwitchShowFragment(Fragment fragment);

    void onShowDialogFragment(DialogFragment fragment);
}
