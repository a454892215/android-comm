package com.test.java;

import com.test.java.util.LogUtil;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 20000; i++) {
            String s = ((char) (Math.random() * 26 + 'a')) + "";

            if(!s.matches("[a-z]")){
                LogUtil.d("   ==============:"+s);
            }


        }

    }

    public static String getTwoDecimalNum(float accuracyValue) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(accuracyValue);
    }
}

