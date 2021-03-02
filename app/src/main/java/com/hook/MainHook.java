package com.hook;

import android.app.Application;


import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        try {
            XLogUtil.d("===1===MainHook=======handleLoadPackage========packageName:" + loadPackageParam.packageName + " 进程名：" + loadPackageParam.processName);
            XposedHelpers.findAndHookMethod(Application.class, "onCreate", new XC_MethodHook() {
                private boolean isExecute = true;

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);

                }
            });

        } catch (Throwable e) {
            XLogUtil.d(e);
        }

    }


}
