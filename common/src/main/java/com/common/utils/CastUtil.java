package com.common.utils;

/**
 * Author:  L
 * Description: No
 */

public class CastUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
