package com.common.hotfix;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.common.comm.version_update.FileDownloadHelper;
import com.common.comm.version_update.Md5Utils;
import com.common.comm.version_update.OnFileDownloadListener;
import com.common.utils.LogUtil;


import java.io.File;
import java.lang.ref.WeakReference;

public class HotFixCallback implements Application.ActivityLifecycleCallbacks {
    private int foreAppCount;
    private WeakReference<Activity> weakReference;
    private HotFixHandler hotFixHandler = new HotFixHandler();
    private BaseHotFix baseHotFix;

    public void init(Application app) {
        String dexFileName = app.getPackageName().replace(".", "_") + ".dex";
        String dexDir = app.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();//dex 保存目录
        String inDexFullPath = dexDir + File.separator + dexFileName;
        File oldFile = new File(inDexFullPath);
        String oldFileMD5 = null;
        if (oldFile.exists()) { //如果文件存在 直接初始化
            oldFileMD5 = Md5Utils.getFileMD5(oldFile);
            startInit(app, inDexFullPath, dexDir);
        }

        String finalOldFileMD5 = oldFileMD5;

        String dexUrl = "http://www.baidu.com/download/Android/dex/qpxm/" + dexFileName;
        LogUtil.i("================dexUrl:" + dexUrl);
        FileDownloadHelper.load(dexUrl, inDexFullPath, new OnFileDownloadListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onProgress(float progress, long total) {
            }

            @Override
            public void onCompleted(File file) {
                if (finalOldFileMD5 == null || !finalOldFileMD5.equals(Md5Utils.getFileMD5(file))) {
                    startInit(app, inDexFullPath, dexDir);
                } else {
                    LogUtil.d("===dex文件下载完毕但是新旧文件相同===");
                }
            }

            @Override
            public void onError(Throwable throwable) {
            }
        });
    }

    private void startInit(Application app, String inDexFullPath, String dexDir) {
        try {
            hotFixHandler.init(app, inDexFullPath, dexDir);
            baseHotFix = hotFixHandler.getBaseHotFix();
            if (baseHotFix != null) baseHotFix.onAppCreate(app);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void onSwitchShowFragment(Fragment fragment) {
        if (baseHotFix!= null) baseHotFix.onSwitchShowFragment(fragment);
    }

    public void onShowDialogFragment(DialogFragment fragment) {
        if (baseHotFix != null) baseHotFix.onShowDialogFragment(fragment);
    }

    private Activity getCurrentActivity() {
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }


}
