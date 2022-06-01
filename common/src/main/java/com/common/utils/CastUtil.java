package com.common.utils;

/**
 * Author:  L
 * Description: No
 */                    android:layout_marginStart="5dp"

public class CastUtil {
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
