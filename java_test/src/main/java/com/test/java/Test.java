package com.test.java;


import com.test.java.util.FileUtil;
import com.test.java.util.LogUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<String> fileList = FileUtil.getAllChildFile("D:\\work\\AndroidProjects\\CommonLibaray\\Common\\app\\src\\main\\res\\layout");
        for (int i = 0; i < fileList.size(); i++) {
            LogUtil.d("==========file:" + fileList.get(i));
            try {
                //简写如下
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(i)), StandardCharsets.UTF_8));
                String line;
                while ((line = br.readLine()) != null) {
                    LogUtil.d("=============:" + line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

