package com.common.hotfix;

import android.content.Context;

import com.common.utils.LogUtil;

import java.io.File;

import dalvik.system.DexClassLoader;

public class HotFixHandler {
    private Class<?> class_HotFix_1;

    public void init(Context context, String dexPath, String optimizedDirectory) {
        try {
            File file = new File(dexPath);
            if (file.exists()) {
                LogUtil.i("=========== HotFix===开始初始化================");
                DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, context.getClassLoader());
                class_HotFix_1 = dexClassLoader.loadClass("hotfix.fix.HotFix_1");
                LogUtil.i("=========== HotFix===初始化成功================");
            } else {
                LogUtil.i("====HotFix===:dexPath:" + dexPath + " 不存在 不进行初始化");
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    BaseHotFix getBaseHotFix() {
        try {
            if (class_HotFix_1 != null) {
                return (BaseHotFix) class_HotFix_1.newInstance();
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }
}
