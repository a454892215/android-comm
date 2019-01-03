package com.common.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Author:  Pan
 * CreateDate: 2019/1/3 10:57
 * Description: No
 */
@SuppressWarnings("unused")
public class NumberUtil {
    public static String getOneDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }

    /**
     * 0 指前面补充零
     * numLength 字符总长度为 formatLength
     */
    public static String getNum(int num, int numLength) {
        return String.format("%0" + numLength + "d", num);
    }
}
