package com.test.java;

import com.test.java.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        String regEx = "[^a-zA-Z0-9]";  //只能输入字母或数字
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("abc你好吗d哈哈123.");
        while (matcher.find()) {
            String group = matcher.group();
            LogUtil.d(group);
        }
        //     String str = matcher.replaceAll("").trim();
        //     LogUtil.d(str);
    }
}

