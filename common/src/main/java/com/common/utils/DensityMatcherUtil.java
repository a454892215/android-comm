package com.common.utils;


import android.app.Activity;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

public class DensityMatcherUtil {

    /**
     * @param activity        activity
     * @param size            用来参照的的宽度或者高度
     * @param isBaseOnLongest 是否基于最长一边，默认false 表示基于垂直状态的手机宽度，否则基于高度
     */
    public static void onActivityCreate(@NonNull Activity activity, float size, boolean isBaseOnLongest) {
        try {
            int baseOnSize;
            DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            if (isBaseOnLongest) { //基于最长一边
                baseOnSize = displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
            } else { //基于最短一边
                baseOnSize = displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels;
            }
            float density = baseOnSize / size;
            float scaledDensity = density * (displayMetrics.scaledDensity / displayMetrics.density);
            int heightPixels = activity.getApplication().getResources().getDisplayMetrics().heightPixels;
            displayMetrics.density = density;
            displayMetrics.scaledDensity = scaledDensity;
            displayMetrics.densityDpi = Math.round(160 *  displayMetrics.density);//每英寸包含像素数
            LogUtil.i("========setDefault========== density:" + density + "  scaledDensity:"
                    + scaledDensity + " baseOnSize:"
                    + baseOnSize + " BASE_SIZE:" + size +" heightPixels:"+heightPixels);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


}
