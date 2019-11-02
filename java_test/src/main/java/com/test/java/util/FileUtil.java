package com.test.java.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

    public static List<String> getAllChildFile(String directionPath) {
        File file = new File(directionPath);
        File[] files = file.listFiles();
        if (files == null) {
            LogUtil.e("空目录");
            return null;
        }
        List<String> list = new ArrayList<>();
        for (File value : files) {
            list.add(value.getAbsolutePath());
        }
        return list;
    }

    public static void readPerLine(String fileName){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                LogUtil.d("=============:" + line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
