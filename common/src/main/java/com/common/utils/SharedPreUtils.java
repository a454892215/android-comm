package com.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.common.CommApp;


@SuppressWarnings("unused")
public class SharedPreUtils {
    private static final String fileName = "sp_file";

    public static Boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sharedPreferences = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getString(String key, String defaultValue) {
        SharedPreferences sharedPreferences = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static float getFloat(String key, float defaultValue) {
        SharedPreferences sharedPreferences = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static long getLong(String key) {
        SharedPreferences sharedPreferences = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    public static long getLong(String key, int defaultValue) {
        SharedPreferences sharedPreferences = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static SharedPreferences.Editor getSpEditor() {
        return CommApp.app.getSharedPreferences(fileName, Context.MODE_PRIVATE).edit();
    }
}
