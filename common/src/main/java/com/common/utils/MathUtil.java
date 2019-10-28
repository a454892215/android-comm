package com.common.utils;

import java.math.BigDecimal;

/**
 * Author:  L
 * Description: No
 */

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
}
