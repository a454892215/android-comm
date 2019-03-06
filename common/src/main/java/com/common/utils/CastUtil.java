package com.common.utils;

/**
 * Author:  Pan
 * Description: No
 */

public class CastUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
