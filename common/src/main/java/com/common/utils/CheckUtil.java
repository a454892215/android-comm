package com.common.utils;

import android.content.Context;

public class CheckUtil {

    public static boolean checkAmount(String currentAmount, String min, String max, Context context) {
        min = StringUtil.isFloat(min) ? min : "0";
        max = StringUtil.isFloat(max) ? max : "1000000";
        float minAmount = Float.parseFloat(min);
        float maxAmount = Float.parseFloat(max);
        if (StringUtil.isFloat(currentAmount)) {
            float amount_f = Float.parseFloat(currentAmount);
            if (amount_f < minAmount) {
                ToastUtil.showShort("金额不能小于" + min + "元");
            } else if (amount_f > maxAmount) {
                ToastUtil.showShort("金额不能大于" + max + "元");
            } else {
                return true;
            }
        } else {
            ToastUtil.showShort("请输入合法金额");
        }
        return false;
    }

}
