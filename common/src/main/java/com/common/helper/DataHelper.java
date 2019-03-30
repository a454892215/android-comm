package com.common.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataHelper {

    public static List<Map<String, String>> getMapList(String[] names) {
        List<Map<String, String>> list = new ArrayList<>();
        for (String name : names) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", name);
            list.add(map);
        }
        return list;
    }

}
