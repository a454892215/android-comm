package com.test.util.pose;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.test.util.BuildConfig;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Author:  L
 * CreateDate: 2019/1/19 11:53
 * Description: No
 */

public class XPosedTest implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XPLogUtil.i("=====handleLoadPackage======packageName:" + loadPackageParam.packageName + " 进程名：" + loadPackageParam.processName);
        try {
            onTestApp(loadPackageParam);
        } catch (Exception e) {
            XPLogUtil.e(e);
        }
    }


    private void onTestApp(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException {
        if (lpparam.packageName.equals("com.test.product_1")) {

            //示例 1
            Class clazz = lpparam.classLoader.loadClass("com.test.util.XposedTestActivity");
            XposedHelpers.findAndHookMethod(clazz, "getText", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult("你被劫持了 ！2" + BuildConfig.app_info);
                }
            });

            // 示例 2 hook activity
            XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    XPLogUtil.i("activity hook 成功:" + activity.getClass().getSimpleName());
                }
            });

            // 示例 3 hook activity
            XposedHelpers.findAndHookConstructor(View.class, "setOnClickListener", View.OnClickListener.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                      XPLogUtil.i("View hook 成功:");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });

        }
    }
}
