package com.common.utils;

import android.annotation.SuppressLint;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.base.BaseActivity;
import com.common.comm.timer.MyCountDownTimer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;


public class NetDelayUtils {
    private TextView tv;
    private ImageView iv_wifi;
    private String baseUrl = "www.baidu.com";
    private WeakReference<BaseActivity> weakReference;

    public NetDelayUtils(BaseActivity activity, TextView tv, ImageView iv_wifi) {
        weakReference = new WeakReference<>(activity);
        this.tv = tv;
        this.iv_wifi = iv_wifi;
    }

    public void startShowNetSpeed() {
        MyCountDownTimer myCountDownTimer = new MyCountDownTimer(Integer.MAX_VALUE, 3000);
        myCountDownTimer.setOnTickListener((time, count) -> start());
        BaseActivity baseActivity = weakReference.get();
        baseActivity.addOnPauseListener(myCountDownTimer::cancel);
        myCountDownTimer.start();
    }

    //   private long lastTime = 0;
    private void start() {
        //  LogUtil.d("===========ping============:" + ((System.currentTimeMillis() - lastTime) / 1000f));
        //   lastTime = System.currentTimeMillis();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                Process p = Runtime.getRuntime().exec("ping -c 1 " + baseUrl.replace("http://", ""));
                p.waitFor();
                int exitValue = p.exitValue();
                StringBuilder info = new StringBuilder();
                if (exitValue == 0) {
                    BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String str;
                    while ((str = buf.readLine()) != null) {
                        if (str.contains("avg")) info.append("\n").append(str);
                    }
                    handlePingResult(info.toString());
                    buf.close();
                } else {
                    LogUtil.e("========ping==========exitValue:" + exitValue);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void handlePingResult(String pingResult) {
      //  LogUtil.d("========ping===========pingResult:" + pingResult);
        if (pingResult.contains("avg")) {
            int i = pingResult.indexOf("/", 20);
            int j = pingResult.indexOf(".", i);
            String time = pingResult.substring(i + 1, j);
            if (weakReference.get() != null && weakReference.get().isShowing()) {
                weakReference.get().runOnUiThread(() -> {
                    tv.setText(time + "ms");
                    if (StringUtil.isInteger(time)) {
                        int time_i = Integer.parseInt(time);
                        if (time_i < 150) {
                          //  iv_wifi.setImageResource(R.mipmap.ic_wifi);
                            tv.setTextColor(0xFF38AE0A);
                        } else if (time_i < 300) {
                          //  iv_wifi.setImageResource(R.mipmap.ic_wifi_2);
                            tv.setTextColor(0xFFFBED64);
                        } else {
                       //     iv_wifi.setImageResource(R.mipmap.ic_wifi_3);
                            tv.setTextColor(0xFFE64129);
                        }
                    }
                });
            }
        }
    }
}
