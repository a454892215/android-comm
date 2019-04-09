package com.common.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestEntity {

    public static List<Map<String, String>> getList() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "数据：" + i);
            list.add(map);
        }
        return list;
    }

    public static List<Map<String, String>> getList(int count) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", "数据：" + i);
            list.add(map);
        }
        return list;
    }
}
