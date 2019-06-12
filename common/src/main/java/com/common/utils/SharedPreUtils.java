package com.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;

@SuppressWarnings("unused")
public class SharedPreUtils {
    private static final String fileName = "sp_file";

    private static WeakReference<Context> contextRef;

    public static void initSp(Context context) {
        contextRef = new WeakReference<>(context);
    }

    //带默认值
    public static Boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    //不带默认值
    public static Boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static float getFloat(String key) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0.0f);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static long getLong(String key) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static long getLong(String key, int defaultValue) {
        SharedPreferences sharedPreferences = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static SharedPreferences.Editor getSpEditor() {
        return contextRef.get().getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
    }
}
