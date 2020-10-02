package com.common.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    // 获取震荡数, 相对0 轴对称
    public static List<Float> getZhenD(float start, float dis) {
        List<Float> list = new ArrayList<>();
        list.add(start);
        int size = list.size();
        while (start != 0) {
            if (start > 0) {
                // 其下次震荡数是小于等于0的数
                start = -start + dis;
                if (start > 0) start = 0;
            } else {
                // 其下次震荡数是大于等于0的数
                start = -start - dis;
                if (start < 0) start = 0;
            }
            list.add(start);
        }
        return list;
    }

    /**
     * nearRate : 接近系数
     */
    public static float getNearNum(float start, float to, float nearRate) {
        return start + (to - start) * nearRate;
    }
}
