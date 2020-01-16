package com.common.hotfix;

import android.content.Context;

import com.common.utils.LogUtil;

import dalvik.system.DexClassLoader;

public class HotFixHandler {
   private Class<?> class_HotFix_1;

    public void init(Context context) {
        try {
            String dexPath = "";
            String optimizedDirectory = ""; //解压目录
            DexClassLoader dexClassLoader = new DexClassLoader(dexPath, optimizedDirectory, null, context.getClassLoader());
            class_HotFix_1 = dexClassLoader.loadClass("hotfix.fix.HotFix_1");
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    BaseHotFix getBaseHotFix(){
        try {
           return (BaseHotFix)class_HotFix_1.newInstance();
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }
}
