package com.common.utils;

/**
 * Author:  Pan
 * Description: No
 */

public class FastClickUtil {
    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    public static boolean isFastClick(int time) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= time) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
