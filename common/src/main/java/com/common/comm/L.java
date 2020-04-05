package com.common.comm;

import android.content.Context;

import com.common.R;

/**
 * Author: Pan
 * 2019/10/6
 * Description:
 */
public class L {
    public static float dp_1;

    public static final String split = "&";

    public static void init(Context context) {
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
    }
}
