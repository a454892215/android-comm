package com.common.utils;

import android.view.View;

/**
 * Author:  Pan
 * CreateDate: 2018/12/31 17:21
 * Description: No
 */

public class ViewUtil {

    public static boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        return y >= top && y <= bottom && x >= left
                && x <= right;
    }
}
