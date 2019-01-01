package com.common.utils;
import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class SharedPreUtils {
private static final String fileName = "sp_file";

    public static Boolean getBoolean(Context context, String key,boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
       return sharedPreferences.getBoolean(key, defValue);
    }

    public static void putBoolean(Context context, String key, Boolean value){
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,Context.MODE_PRIVATE).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }

    public static String getString(Context context, String key,String defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,Context.MODE_PRIVATE).edit();
        editor.putString(key,value);
        editor.apply();
    }

    public static float getFloat(Context context,String key,float defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key,defaultValue);
    }

    public static void putFloat(Context context,String key,float value){
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,Context.MODE_PRIVATE).edit();
        editor.putFloat(key,value);
        editor.apply();
    }

    public static long getLong(Context context,String key,long defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key,defaultValue);
    }

    public static void putLong(Context context,String key,long value){
        SharedPreferences.Editor editor = context.getSharedPreferences(fileName,Context.MODE_PRIVATE).edit();
        editor.putLong(key,value);
        editor.apply();
    }
}
