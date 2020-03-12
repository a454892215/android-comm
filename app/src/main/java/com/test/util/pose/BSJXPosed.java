package com.test.util.pose;

import android.app.Activity;

import android.os.Bundle;
import android.view.View;

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


        if (!lpparam.packageName.equals("com.bishijie")) {
            XPLogUtil.i("============币市界hook 成功: " + lpparam.packageName);
            /*  hook  activity */
            XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XPLogUtil.i("=========== Activity   hook 成功: " + lpparam.packageName);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Activity activity = (Activity) param.thisObject;
                    XPLogUtil.i("activity:" + activity.getClass().getName());

                }
            });

            /*  hook  fragment */
/*            XposedHelpers.findAndHookConstructor(Fragment.class,  new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XPLogUtil.i("============币市界 Fragment   hook 成功: ");
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Fragment fragment = (Fragment) param.thisObject;
                    XPLogUtil.i("Fragment:" + fragment.getClass().getName());


                }
            });*/


            /*  hook  RecyclerView */
/*            XposedHelpers.findAndHookMethod(RecyclerView.class, "onAttachedToWindow", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XPLogUtil.i("============ RecyclerView   hook 成功: " + lpparam.packageName);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    RecyclerView recyclerView = (RecyclerView) param.thisObject;
                    XPLogUtil.i("RecyclerView:" + recyclerView.getClass().getName());


                }
            });*/

            /*  hook  ListView */
/*            XposedHelpers.findAndHookMethod(ListView.class, "onAttachedToWindow", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    XPLogUtil.i("============ ListView   hook 成功: " + lpparam.packageName);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    ListView listView = (ListView) param.thisObject;
                    XPLogUtil.i("ListView:" + listView.getClass().getName());


                }
            });*/

            /*  hook  TextView */
            XposedHelpers.findAndHookConstructor(String.class, "setOnClickListener", View.OnClickListener.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    //  XPLogUtil.i("============ TextView   hook 成功: " + lpparam.packageName);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    Object obj = param.thisObject;
                    if (obj instanceof View) {
                        XPLogUtil.i("====obj====:" + obj.getClass().getSimpleName());
                    }


                }
            });
        }
    }
}
