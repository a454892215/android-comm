package com.test.util.comm;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2020/11/14
 * Description:
 */
public class TestDataHelper {

    public static List<String> getData(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("测试数据：" + i);
        }
        return list;
    }
}
