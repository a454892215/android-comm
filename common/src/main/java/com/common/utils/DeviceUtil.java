package com.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.security.MessageDigest;

public class DeviceUtil {

    private static String DeviceId = null;

    public static String getUniqueId(Context context) {
        if (DeviceId == null) {
            DeviceId = getDeviceID(context);
        }
        return DeviceId;
    }

    private static String getDeviceID(Context context) {
        try {
          //  if (true) return System.currentTimeMillis() + "";
            //通过wifi获取
         //   @SuppressLint("HardwareIds") String macAddress = getWifiId(context);
          //  if (macAddress != null) return macAddress;
            //不需要权限
            @SuppressLint("HardwareIds") String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            @SuppressLint("HardwareIds") String id = "last" + toMD5(androidID + Build.SERIAL);
            LogUtil.d("==============最终的设备号 id：" + id);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getWifiId(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    @SuppressLint("HardwareIds") String macAddress = connectionInfo.getMacAddress();
                    LogUtil.d("==============wifi的设备号 macAddress：" + macAddress);
                    if ("02:00:00:00:00:00".equals(macAddress)) return null;
                    if (!TextUtils.isEmpty(macAddress)) {
                        macAddress = "wifi" + toMD5(macAddress);
                        return macAddress;
                    }

                }
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }

    private static String toMD5(String text) {
        try {
            //获取摘要器 MessageDigest
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //通过摘要器对字符串的二进制字节数组进行hash计算
            byte[] digest = messageDigest.digest(text.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                //循环每个字符 将计算结果转化为正整数;
                int digestInt = b & 0xff;
                //将10进制转化为较短的16进制
                String hexString = Integer.toHexString(digestInt);
                //转化结果如果是个位数会省略0,因此判断并补0
                if (hexString.length() < 2) {
                    sb.append(0);
                }
                //将循环结果添加到缓冲区
                sb.append(hexString);
            }
            //返回整个结果
            return sb.toString();
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return text;
    }
}