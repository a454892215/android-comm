package com.test.util.pose;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.test.util.BuildConfig;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

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
        } catch (Throwable e) {
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

            // 示例 3 hook View一个参数的构造函数
            XposedHelpers.findAndHookConstructor(View.class, Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i("1个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getSimpleName());
                }
            });

            // 示例 4 hook View2个参数的构造函数
            XposedHelpers.findAndHookConstructor(View.class, Context.class, AttributeSet.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i("2个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getSimpleName());
                }
            });

            // 示例 5 hook View3个参数的构造函数
            XposedHelpers.findAndHookConstructor(View.class, Context.class, AttributeSet.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i("3个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getSimpleName());
                }
            });

            // 示例 5 hook TextView setText个参数的构造函数
            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    TextView tv = (TextView) param.thisObject;
                    XPLogUtil.i(" TextView setText hook 成功:" + tv.getText());
                }
            });

            // 示例 6 hook OkHttpClient
            XposedHelpers.findAndHookConstructor(OkHttpClient.class, OkHttpClient.Builder.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i(" OkHttpClient hook 成功:" );
                }
            });

            // 示例 7 hook ResponseBody.string.()
            XposedHelpers.findAndHookMethod(ResponseBody.class, "string", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i(" ResponseBody.string() hook 成功:");
                }
            });


        }
    }
}
