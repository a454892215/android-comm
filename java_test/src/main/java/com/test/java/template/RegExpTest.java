package com.test.java.template;

import com.test.java.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExpTest {

    public static void main(String[] args) {
        String regEx = "[^a-zA-Z0-9]|a|b|/.";  //对字母数字取反  外加 a,b,.
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
