package com.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;

import com.common.base.BaseActivity;

/**
 * Created by Administrator on 2018/5/11.
 */

public class DensityUtils {

    private DensityUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }



    public static float getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static float getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getRealScreenHeight(BaseActivity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        return dm.heightPixels;
    }


}
