package com.common.utils;

import java.lang.reflect.Constructor;

public class InstanceUtil {
    public static <T> T getInstance(Class<T> typeClass) {
        try {
            return typeClass.newInstance();
        } catch (Exception e) {
            LogUtil.e("反射创建实例异常：" + e);
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getInstance(Class<T> typeClass, Class[] parameterTypes, Object[] parameters) {
        try {
            Constructor<T> constructor = typeClass.getConstructor(parameterTypes);
            return constructor.newInstance(parameters);
        } catch (Exception e) {
            LogUtil.e("反射创建实例异常：" + e);
            e.printStackTrace();
        }
        return null;
    }
}
