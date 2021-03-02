package com.hook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;


import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Author: Pan
 * 2021/3/2
 * Description:
 */
public class HookDemo {


    /**
     * hook构造函数
     */
    public static void hookConstructor() {
        XposedHelpers.findAndHookConstructor(ArrayList.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                XLogUtil.d("afterHookedMethod:" + param.thisObject.getClass().getSimpleName());

            }
        });

        XposedHelpers.findAndHookConstructor(View.class, Context.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                View view = (View) param.thisObject;
                XLogUtil.d("=========View Hook 成功=========view:" + view.getClass().getSimpleName());
                if (view instanceof TextView) {
                    TextView tv = (TextView) param.thisObject;
                    CharSequence text = tv.getText();
                    XLogUtil.d("==================text:" + text);
                }
            }
        });
    }

    /**
     * hook普通函数
     */
    public static void hookMethod() {
        XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {


            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                //  Activity activity = (Activity) param.thisObject;
                XLogUtil.d("Activity hook 成功=====:" + param.thisObject.getClass().getSimpleName());

            }
        });
    }
}
