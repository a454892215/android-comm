package com.example.jpushdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;

import static com.example.jpushdemo.LogUtil.e;

class ExampleUtil {
    static final String PREFS_NAME = "JPUSH_EXAMPLE";
    static final String PREFS_DAYS = "JPUSH_EXAMPLE_DAYS";
    static final String PREFS_START_TIME = "PREFS_START_TIME";
    static final String PREFS_END_TIME = "PREFS_END_TIME";
    private static final String KEY_APP_KEY = "JPUSH_APPKEY";

    static boolean isEmpty(String s) {
        if (null == s)
            return true;
        if (s.length() == 0)
            return true;
        if (s.trim().length() == 0)
            return true;
        return false;
    }
    /**
     * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
     * */
    private final static String MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$";
    static boolean isValidMobileNumber(String s) {
        if(TextUtils.isEmpty(s)) return true;
        Pattern p = Pattern.compile(MOBILE_NUMBER_CHARS);
        Matcher m = p.matcher(s);
        return m.matches();
    }
    // 校验Tag Alias 只能是数字,英文字母和中文
    static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    // 取得AppKey
    static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {
           e(e);
        }
        return appKey;
    }
    
    // 取得版本号
    static String GetVersion(Context context) {
		try {
			PackageInfo manager = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			return manager.versionName;
		} catch (NameNotFoundException e) {
			return "Unknown";
		}
	}

    static void showToast(final String toast, final Context context)
    {
    	new Thread(() -> {
            Looper.prepare();
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }).start();
    }
    
    static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info == null || !info.isConnected());
    }
    
	@SuppressLint("HardwareIds")
    static String getImei(Activity activity) {
        String ret = null;
		try {
            if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // toast("需要动态获取权限");
                ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 11);
            }else{
                // toast("不需要动态获取权限");
                TelephonyManager tm = (TelephonyManager)activity.getSystemService(Context.TELEPHONY_SERVICE);
                if(tm != null){
                    ret = tm.getDeviceId();
                }
            }


		} catch (Exception e) {
			LogUtil.e(e);
		}
		if (isReadableASCII(ret)){
            return ret;
        } else {
            return "";
        }
	}

    private static boolean isReadableASCII(CharSequence string){
        if (TextUtils.isEmpty(string)) return false;
        try {
            Pattern p = Pattern.compile("[\\x20-\\x7E]+");
            return p.matcher(string).matches();
        } catch (Throwable e){
            return true;
        }
    }

    static String getDeviceId(Context context) {
        return JPushInterface.getUdid(context);
    }
}
