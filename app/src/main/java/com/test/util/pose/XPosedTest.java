package com.test.util.pose;


import android.app.Activity;
import android.os.Bundle;

import com.common.utils.ToastUtil;
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
        XPLogUtil.log("packageName: " + loadPackageParam.packageName);
        XPLogUtil.log("=====handleLoadPackage========packageName:" + loadPackageParam.packageName + " 进程名：" + loadPackageParam.processName);
        //    onTestApp(lpparam);
        startHook();
    }

    private void startHook() {
        // hook 所有Activity的onCreate函数
        XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //获取hook的activity 对象
                Activity activity = (Activity) param.thisObject;
                XPLogUtil.log("=========afterHookedMethod======param.method:" + param.method +
                        "  param.thisObject:" + param.thisObject + "   param:" + param);
                activity.runOnUiThread(() -> ToastUtil.showLong(activity, "我是hook的toast3:" + activity.getClass().getSimpleName()));
            }
        });
        //   }


    }

    private void onTestApp(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException {
        if (lpparam.packageName.equals("com.test.product_1")) {
            Class clazz = lpparam.classLoader.loadClass("com.test.util.XposedTestActivity");
            XposedHelpers.findAndHookMethod(clazz, "getText", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                protected void afterHookedMethod(MethodHookParam param) {
                    param.setResult("你被劫持了 ！2" + BuildConfig.app_info);
                }
            });
            XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    ToastUtil.showLong(activity, "我是hook的toast");
                    XPLogUtil.log("activity:" + activity);
                }
            });

        }
    }
}
