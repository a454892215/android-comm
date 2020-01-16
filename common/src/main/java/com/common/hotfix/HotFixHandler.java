package com.common.hotfix;

import android.content.Context;
import android.os.Environment;

import com.common.utils.LogUtil;

import java.util.Objects;

import dalvik.system.DexClassLoader;

public class HotFixHandler {
    private Class<?> class_HotFix_1;

    public void init(Context context, String dexPath, String optimizedDirectory) {
        try {
            DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, context.getClassLoader());
            class_HotFix_1 = dexClassLoader.loadClass("hotfix.fix.HotFix_1");
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    BaseHotFix getBaseHotFix() {
        try {
            return (BaseHotFix) class_HotFix_1.newInstance();
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }

    private static String getDownloadDirPath(Context context) {
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果存在外置储存空间
            try {
                path = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
                LogUtil.d("======1===下载dex保存目录为External缓存目录：" + path);
            } catch (Exception e) {
                LogUtil.e(e);
            }

        } else {//如果不存在
            path = context.getCacheDir().getAbsolutePath();
            LogUtil.d("======3===下载dex保存目录为内部缓存目录：" + path);
        }
        return path;
    }

    public static String getDownloadedDexFullPath(Context context,String dexFileName) {
        return getDownloadDirPath(context) + "/dex/" + dexFileName;
    }
}
