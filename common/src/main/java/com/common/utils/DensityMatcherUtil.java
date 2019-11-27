package com.common.utils;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class DensityMatcherUtil {
    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;

    /**
     * 用来参照的的宽度或者高度
     */
    private static float BASE_SIZE;
    private static boolean IS_BASE_ON_LONGEST = false;

    /**
     * @param application     app
     * @param size            用来参照的的宽度或者高度
     * @param isBaseOnLongest 是否基于最长一边，默认false 表示基于垂直状态的手机宽度，否则基于高度
     */
    public static void init(@NonNull final Application application, float size, boolean isBaseOnLongest) {
        BASE_SIZE = size;
        IS_BASE_ON_LONGEST = isBaseOnLongest;
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        appDensity = appDisplayMetrics.density;
        appScaledDensity = appDisplayMetrics.scaledDensity;
        registerActivityLifecycleCallbacks(application);
        LogUtil.d("====================Density:" + appDensity + "  appScaledDensity:" + appScaledDensity);
    }


    private static void setAppDensity(@NonNull Activity activity) {
        try {
            int baseOnSize;
            if (IS_BASE_ON_LONGEST) { //基于最长一边
                baseOnSize = appDisplayMetrics.widthPixels > appDisplayMetrics.heightPixels ? appDisplayMetrics.widthPixels : appDisplayMetrics.heightPixels;
            } else { //基于最短一边
                baseOnSize = appDisplayMetrics.widthPixels > appDisplayMetrics.heightPixels ? appDisplayMetrics.heightPixels : appDisplayMetrics.widthPixels;
            }
            float density = baseOnSize / BASE_SIZE;
            float scaledDensity = density * (appScaledDensity / appDensity);
            int targetDensityDpi = (int) (160 * density);
            DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
            activityDisplayMetrics.density = density;
            activityDisplayMetrics.scaledDensity = scaledDensity;
            activityDisplayMetrics.densityDpi = targetDensityDpi;
            LogUtil.i("========setDefault========== density:" + density + "  scaledDensity:"
                    + scaledDensity + "  targetDensityDpi:" + targetDensityDpi + " baseOnSize:"
                    + baseOnSize + " BASE_SIZE:" + BASE_SIZE);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


    private static void registerActivityLifecycleCallbacks(Application application) {
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                setAppDensity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


}
