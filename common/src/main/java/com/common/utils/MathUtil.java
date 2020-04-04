package com.common.utils;

import java.math.BigDecimal;

/**
 * Author:  L
 * Description: No
 */
@SuppressWarnings("unused")
public class MathUtil {

    public static float clamp(float num, float min, float max) {
        num = num < min ? min : num;
        num = num > max ? max : num;
        return num;
    }

    public static int clamp(int num, int min, int max) {
        num = num < min ? min : num;
        num = num > max ? max : num;
        return num;
    }


    public static float absClamp(float num, float min, float max) {
        if (num > 0) {
            num = num < min ? min : num;
            num = num > max ? max : num;
        } else {
            num = num < -max ? -max : num;
            num = num > -min ? -min : num;
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
            min = min < value ? min : value;
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
            max = max > value ? max : value;
        }
        return max;
    }
}
