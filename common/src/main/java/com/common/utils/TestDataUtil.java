package com.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author:  L
 * Description: No
 */

public class TestDataUtil {

    public static List<String> getData(int size) {
        ArrayList<String> arrayList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                arrayList.add("test：" + NumberUtil.getNum(i,3) + "-" + (random.nextInt(89) + 10));
            } else {
                arrayList.add("test：" + NumberUtil.getNum(i,3));
            }

        }
        return arrayList;
    }

    public static List<String> getData(int size, int start) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = start; i < size + start; i++) {
            arrayList.add("test：" + NumberUtil.getNum(i,3));
        }
        return arrayList;
    }
}
