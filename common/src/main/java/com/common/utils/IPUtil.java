package com.common.utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;

/**
 * Author: Pan
 * 2021/3/28
 * Description: 获取当前wifi网络或者mobile的IPv4 的IP地址
 */
public class IPUtil {

    private static final IPUtil instance = new IPUtil();

    private IPUtil() {
    }

    public static IPUtil getInstance() {
        return instance;
    }


    private String currentIP = "";

    public String getCurrentIP() {
        return currentIP;
    }

    private void setWifiIP() {
        WifiManager wifiManager = (WifiManager) app.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        currentIP = Formatter.formatIpAddress(ipAddress);
        LogUtil.d(" wifi 网络IP：" + currentIP);
    }

    private void setMobileNetworkIP() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address) {
                        LogUtil.d(" 移动网络IP：" + address.getHostAddress());
                        currentIP = address.getHostAddress();
                        return;
                    }
                }
            }
        } catch (SocketException e) {
            LogUtil.e(e);
        }
    }

    private Application app;

    public void init(Application app) {
        if (this.app == null) {
            this.app = app;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
            app.registerReceiver(networkChangeReceiver, intentFilter);
        }

    }

    private class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager) app.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                switch (networkInfo.getType()) {
                    case TYPE_MOBILE:
                        setMobileNetworkIP();
                        break;
                    case TYPE_WIFI:
                        setWifiIP();
                        break;
                    default:
                        break;
                }
            }
        }
    }


}
