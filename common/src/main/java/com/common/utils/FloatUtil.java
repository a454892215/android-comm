package com.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Author:  L
 * CreateDate: 2019/1/3 10:57
 * Description: No
 */
@SuppressWarnings("unused")
public class FloatUtil {
    public static String getOneDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }

    public static String getTwoDecimalNum(String floatValue) {
        if (StringUtil.isFloat(floatValue)) {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
            return df.format(Float.parseFloat(floatValue));
        } else {
            LogUtil.e("非法数据，不能保留2位小数");
            return "0.00";
        }
    }


    /**
     * 0 指前面补充零
     * numLength 字符总长度为 formatLength
     */
    public static String getNum(int num, int numLength) {
        return String.format("%0" + numLength + "d", num);
    }

    /**
     * 获取百分数
     */
    public static String getPercent(float startValue, float endValue) {
        return (((endValue - startValue) / startValue) * 100) + "%";
    }

    /**
     * 从格式化字符串获取第1个float
     */
    public static float getF1(String startValue) {
        return Float.parseFloat(startValue.split("-")[0]);
    }

    /**
     * 从格式化字符串获取第2个float
     */
    public static float getF2(String startValue) {
        return Float.parseFloat(startValue.split("-")[1]);
    }
}
