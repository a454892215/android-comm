package com.test.java;

import com.test.java.util.LogUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        Executors.newSingleThreadExecutor().execute(()-> new Test().compute());
    }

    private void compute() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            /*for (int j = 0; j < 1000; j++) {

            }*/
            Date date = new Date();
        }
        long end = System.currentTimeMillis();
        long t = end - start;
        System.out.println("  cost time:" + t);
    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

