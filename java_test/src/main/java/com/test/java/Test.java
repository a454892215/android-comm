package com.test.java;

import com.test.java.util.LogUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

    public static void main(String[] args) {
        DecimalFormat df1 = new DecimalFormat("0.00");
        LogUtil.d(df1.format(0.1));

    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

