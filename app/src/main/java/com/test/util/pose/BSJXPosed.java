package com.test.util.pose;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
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

    static void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {


        if (true) {
            XPLogUtil.i("============币市界hook 成功: " + lpparam.packageName);

            // 示例 3 hook View一个参数的构造函数
            XposedHelpers.findAndHookConstructor(View.class, Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    if (param.thisObject instanceof RecyclerView
                            || param.thisObject instanceof ListView) {
                        XPLogUtil.i("1个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getSimpleName());
                    }

                }
            });

            // 示例 4 hook View2个参数的构造函数
/*            XposedHelpers.findAndHookConstructor(View.class, Context.class, AttributeSet.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    XPLogUtil.i("2个参数的View构造函数 hook 成功:" + param.thisObject.getClass().getSimpleName());
                }
            });*/

            // 示例 5 hook TextView setText个参数的构造函数
            XposedHelpers.findAndHookMethod(TextView.class, "setText", CharSequence.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);

                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                  //  TextView tv = (TextView) param.thisObject;
                  //  XPLogUtil.i(" TextView setText hook 成功:" + tv.getText());
                }
            });
        }
    }
}
