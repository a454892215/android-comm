package com.common.utils;

import com.common.comm.L;

import java.math.BigDecimal;
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

    public static float get2DecimalNum(double floatValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
        String format = df.format(floatValue);
        return Float.parseFloat(format);
    }

    public static String get2DecimalNum2(double floatValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
        return df.format(floatValue);
    }

    public static String get4DecimalNum(String floatValue) {
        if (StringUtil.isFloat(floatValue)) {
            DecimalFormat df = new DecimalFormat("0.0000");
            df.setRoundingMode(RoundingMode.HALF_UP);//四舍五入
            return df.format(Float.parseFloat(floatValue));
        } else {
            LogUtil.e("非法数据，不能保留4位小数");
            return "0.0000";
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
     * 获取1位进度的数
     */
    public static float get1D(float value) {
        BigDecimal bd = new BigDecimal(value + "");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * 获取2位进度的数
     */
    public static float get2D(float value) {
        return ((int) (value * 100)) / 100f;
    }

    /**
     * 获取3位进度的数
     */
    public static float get3D(float value) {
        BigDecimal bd = new BigDecimal(value + "");
        bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * 获取保留2位小数的百分数
     */
    public static float get2DPercent(float startValue, float endValue) {
        return ((int) ((((endValue - startValue) / startValue) * 100) * 100)) / 100f;
    }

    /**
     * 获取保留3位小数的百分数
     */
    public static float get3DPercent(float startValue, float endValue) {
        return ((int) ((((endValue - startValue) / startValue) * 100) * 1000)) / 1000f;
    }

    /**
     * 从格式化字符串获取第1个float
     */
    public static float getF1(String startValue) {
        return Float.parseFloat(startValue.split(L.split)[0]);
    }

    /**
     * 从格式化字符串获取第2个float
     */
    public static float getF2(String startValue) {
        return Float.parseFloat(startValue.split(L.split)[1]);
    }

    /**
     * 从格式化字符串获取第1个double
     */
    public static double getD1(String startValue) {
        return Double.parseDouble(startValue.split(L.split)[0]);
    }

    /**
     * 从格式化字符串获取第2个double
     */
    public static double getD2(String startValue) {
        return Double.parseDouble(startValue.split(L.split)[1]);
    }
}
