package com.hotfix;

import android.app.Activity;
import android.app.Application;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.common.hotfix.BaseHotFix;
import com.common.utils.LogUtil;

@SuppressWarnings("unused")
public class HotFix_1 implements BaseHotFix {
    private static final float versionNum = 1.1f;

    @Override
    public void onAppCreate(Application application) {
        LogUtil.i("================HotFix_1 version:" + versionNum);
    }

    @Override
    public void onActivityCreate(Activity activity) {
    }

    @Override
    public void onActivityResume(Activity activity) {
        LogUtil.i("=======onActivityResume=========HotFix_1 version:" + versionNum);
    }

    @Override
    public void onActivityPause(Activity activity) {

    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }

    @Override
    public void onSwitchShowFragment(Fragment fragment) {

    }

    @Override
    public void onShowDialogFragment(DialogFragment fragment) {

    }
}
