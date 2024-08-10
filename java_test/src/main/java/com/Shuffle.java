package com;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Shuffle {

    /**
     * 使用1-100的数字，随机排序，生成一个长度为100的数组。 使用java实现
     */
    public static void main(String[] args) {
        Random random = new Random();
        // 使用Set去重，确保生成的数字不重复
        ArrayList<Integer> list = new ArrayList<>();
        while (list.size() < 100) {
            int num = random.nextInt(100) + 1; // 生成1-2200的随机数
            list.add(num);
        }


        // 输出生成的数组
        System.out.println(list);
    }
}
