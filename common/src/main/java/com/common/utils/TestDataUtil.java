package com.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author:  Pan
 * CreateDate: 2018/12/20 15:35
 * Description: No
 */

public class TestDataUtil {

    public static List<String> getData(int size) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            arrayList.add("测试数据："+i);
        }
        return arrayList;
    }

    public static List<String> getDataNoorder(int size) {
        ArrayList<String> arrayList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            arrayList.add("测试数据："+ (random.nextInt(88)+10));
        }
        return arrayList;
    }


    public static List<String> getData(int size, int start) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = start; i < size + start ; i++) {
            arrayList.add("测试数据："+i);
        }
        return arrayList;
    }
}
