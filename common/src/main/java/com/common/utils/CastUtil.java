package com.common.utils;

/**
 * Author:  Pan
 * CreateDate: 2018/12/18 8:39
 * Description: No
 */

public class CastUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
