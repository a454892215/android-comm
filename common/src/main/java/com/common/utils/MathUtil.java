package com.common.utils;

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
}
