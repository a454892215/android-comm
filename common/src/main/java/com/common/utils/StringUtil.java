package com.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

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
}
