package com.test.util.pose;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Author: Pan
 * 2020/3/11
 * Description:
 */
class BSJXPosed {

    static void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {


        if (true) {
            XPLogUtil.i("============币市界hook 成功: " + lpparam.packageName);

            // 示例 3 hook View一个参数的构造函数

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
                    if (activity.getClass().getName().contains("StrategyDetailActivity")) {
                        XPLogUtil.i("StrategyDetailActivity hook 成功:");
                        hookSetText();
                    }

                }
            });

            XposedHelpers.findAndHookConstructor(View.class, Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (param.thisObject.getClass().getName().contains("RecyclerView")
                            || param.thisObject.getClass().getName().contains("ListView")) {

                        XPLogUtil.i("1个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getName());

                    }

                }
            });


        }
    }

    private static void hookSetText() {
        XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);

            }

            @SuppressLint("ResourceType")
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                TextView tv = (TextView) param.thisObject;
                String text = tv.getText().toString();
                if (text.contains("%") || text.contains("¥") || tv.getId() == 2131757155) return;
                XPLogUtil.i(" TextView setText hook 成功:" + text + "  id :" + tv.getId());
            }
        });
    }
}
