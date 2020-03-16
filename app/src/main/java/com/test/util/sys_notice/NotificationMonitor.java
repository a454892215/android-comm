package com.test.util.sys_notice;

import android.app.Notification;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.common.utils.LogUtil;
import com.test.util.App;

/**
 * Author: Pan
 * 2020/3/16
 * Description:
 */
public class NotificationMonitor extends NotificationListenerService {
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        if(notificationPkg.contains("bishijie")){
            // 获取接收消息的抬头
            String title = extras.getString(Notification.EXTRA_TITLE);
            // 获取接收消息的内容
            String content = extras.getString(Notification.EXTRA_TEXT);
            SystemRing.getInstance().play(0);
            LogUtil.i("消息标题 " + title + " content:" + content);
        }

    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        if(notificationPkg.contains("bishijie")){
            // 获取接收消息的抬头
            String title = extras.getString(Notification.EXTRA_TITLE);
            // 获取接收消息的内容
            String content = extras.getString(Notification.EXTRA_TEXT);
            SystemRing.getInstance().stop(0);
            LogUtil.i("消息标题: " + title + " content: " + content);
        }

    }
}

