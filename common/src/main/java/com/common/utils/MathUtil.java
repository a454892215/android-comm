package com.common.utils;

import java.math.BigDecimal;

/**
 * Author:  L
 * Description: No
 */
@SuppressWarnings("unused")
public class MathUtil {

    public static float clamp(float num, float min, float max) {
        num = Math.max(num, min);
        num = Math.min(num, max);
        return num;
    }

    public static int clamp(int num, int min, int max) {
        num = Math.max(num, min);
        num = Math.min(num, max);
        return num;
    }


    public static float absClamp(float num, float min, float max) {
        if (num > 0) {
            num = Math.max(num, min);
            num = Math.min(num, max);
        } else {
            num = Math.max(num, -max);
            num = Math.min(num, -min);
        }
        return num;
    }

    public static float mul(float... values) {
        BigDecimal total = new BigDecimal(String.valueOf(1));
        for (float value : values) {
            total = total.multiply(new BigDecimal(String.valueOf(value)));
        }
        return total.floatValue();
    }

    public static float sub(float value1, float value2) {
        return new BigDecimal(String.valueOf(value1)).subtract(new BigDecimal(String.valueOf(value2))).floatValue();
    }

    /**
     * 获取最小值
     */
    public static float getMinValue(float... values) {
        float min = Float.MAX_VALUE;
        int length = values.length;
        for (float value : values) {
            min = Math.min(min, value);
        }
        return min;
    }

    /**
     * 获取最大值
     */
    public static float getMaxValue(float... values) {
        float max = Float.MIN_VALUE;
        int length = values.length;
        for (float value : values) {
            max = Math.max(max, value);
        }
        return max;
    }
}
