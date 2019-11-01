package com.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:  Pan
 * CreateDate: 2019/7/10 16:00
 * Description: No
 */

public class StringUtil {

    public static String getThrowableInfo(Throwable e) {
        String text = ":null:";
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            text = sw.toString();
            sw.close();
        } catch (Exception e1) {
            LogUtil.e("===============e:" + e);
            e1.printStackTrace();
        }

        return text;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 包含int 类型
     */
    public static boolean isFloat(String str) {
        LogUtil.d("================str:"+str);
        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否是手机号码
     */
    public static boolean isMobileNum(String mobiles) {
        Pattern p = Pattern.compile("[1][3456789]\\d{9}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

}
