package com.common.utils;


import android.app.Activity;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import com.common.R;
import com.common.comm.L;

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
            LogUtil.d("=====1====displayMetrics:" + displayMetrics);
            LogUtil.d("==========displayMetrics=====1dp的实际像素数是：" + 1 * displayMetrics.density + "  dp:" + L.dp_1);
            if (isBaseOnLongest) { //基于最长一边
                baseOnSize = displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.widthPixels : displayMetrics.heightPixels;
            } else { //基于最短一边
                baseOnSize = displayMetrics.widthPixels > displayMetrics.heightPixels ? displayMetrics.heightPixels : displayMetrics.widthPixels;
            }
            float density = baseOnSize / size;
            float scaledDensity = density * (displayMetrics.scaledDensity / displayMetrics.density);
            //  int heightPixels = activity.getApplication().getResources().getDisplayMetrics().heightPixels;
            displayMetrics.density = density;
            displayMetrics.scaledDensity = density; //字体的缩放因子 正常情况和density相同 系统调整大小后会改变
            displayMetrics.densityDpi = Math.round(160 * density);//每英寸包含像素数 对dp 没有隐形 对默认尺寸会有影响


            LogUtil.d("=====2====displayMetrics:" + displayMetrics);
            float dp_1 = activity.getResources().getDimension(R.dimen.dp_1);
            LogUtil.d("==========displayMetrics=====1dp的实际像素数是：" + 1 * displayMetrics.density + "  dp:" + dp_1 + " baseOnSize:" + baseOnSize);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


}
