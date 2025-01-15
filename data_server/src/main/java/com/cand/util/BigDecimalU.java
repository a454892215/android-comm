package com.cand.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalU {

    /**
     *
     * @param start 开始价格
     * @param end 结束价格
     * @return 百分比幅度
     */
    public static double getFd(BigDecimal start, BigDecimal end){
        if (start == null || end == null || start.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Invalid input: start and end must be non-null and start cannot be zero.");
        }
        BigDecimal difference = end.subtract(start);  // 计算价格差
        BigDecimal percentage = difference.divide(start, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));  // 计算百分比
        return percentage.doubleValue();  // 返回百分比
    }


}
