package com.common.utils;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.common.base.BaseActivity;

/**
 * Created by dl on 2019/3/5.
 */

public class SystemUtils {


    public static boolean setClipData(String label, String text, BaseActivity activity) {
        try {
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(label, text);
            if (cm != null) {
                cm.setPrimaryClip(clipData);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("LLpp:" + e.toString());
            return false;
        }
    }

    public static boolean isNetWorkConnected(Context context) {
        boolean isConnected = true;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) return true;
            @SuppressLint("MissingPermission") NetworkInfo activeInfo = cm.getActiveNetworkInfo();
            isConnected = activeInfo != null && activeInfo.isAvailable() && activeInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

}
