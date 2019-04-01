package com.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Description: Toast工具类
 */

public class ToastUtil {

    public static void showShort(Context context, CharSequence message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public static void showLong(Context context, CharSequence message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
