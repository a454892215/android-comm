package com.test.java;

import com.test.java.util.LogUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Test {

    public static void main(String[] args) {

        LogUtil.d("=============getTwoDecimalNum:" + getTwoDecimalNum(0.226f));
    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

