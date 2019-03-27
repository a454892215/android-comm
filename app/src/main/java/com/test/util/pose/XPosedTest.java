package com.test.util.pose;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Author:  L
 * CreateDate: 2019/1/19 11:53
 * Description: No
 */

public class XPosedTest  implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.test.util")) {
            Class clazz = lpparam.classLoader.loadClass("com.test.util.XposedTestActivity");
            XposedHelpers.findAndHookMethod(clazz, "getText", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }
                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult("你被劫持了 ！");
                }
            });
        }
    }
}
