package com.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import com.common.base.BaseActivity;

@SuppressWarnings("unused")
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

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager im = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } else {
                LogUtil.e("=========InputMethodManager = null==============");
            }
        }
    }

    public static void hideBottomVirtualKey(AppCompatActivity activity) {
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static long getPackageFirstInstallTime(Context context) {
        String name = context.getPackageName();
        long time = 0;
        try {
            time = context.getPackageManager().getPackageInfo(name, 0).firstInstallTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static long getPackageLastUpdateTime(Context context) {
        String name = context.getPackageName();
        long time = 0;
        try {
            time = context.getPackageManager().getPackageInfo(name, 0).lastUpdateTime;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static boolean isFirstInstall(Context context) {
        return getPackageFirstInstallTime(context) == getPackageLastUpdateTime(context);
    }
}
