package com.common.comm.input_check;

import android.widget.TextView;

import com.common.utils.LogUtil;

public class CheckUtil {

    public static boolean check(InputType[] types, TextView[] tvArr) {
        if (types.length != tvArr.length) {
            LogUtil.e("检测TextView数组和其对应类型 长度必须一致");
            return false;
        }
        for (int i = 0; i < tvArr.length; i++) {
            String inputText = tvArr[i].getText().toString();
            boolean check = types[i].check(inputText);
            if (!check) {
                return false;
            }
        }
        return true;
    }
}
