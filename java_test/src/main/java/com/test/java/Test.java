package com.test.java;

import com.test.java.util.LogUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

    public static void main(String[] args) {
        Timer timer = new Timer();
        for (int i = 0; i < 5; i++) {
         //   int random = new Random().nextInt(3) + 1;
            int finalI = i;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LogUtil.d("===============random:" + finalI);
                }
            }, 1000 * i);

        }


    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

