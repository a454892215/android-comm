package com.test.util.sys_notice;

import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;

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

        // 获取接收消息的抬头
        String title = extras.getString(Notification.EXTRA_TITLE);
        String content = extras.getString(Notification.EXTRA_TEXT);
        String msg = "===接受到消息====消息标题 " + title + " content:" + content + "  :" + notificationPkg;
        LogUtil.i(msg);
        ToastUtil.showLong(msg);
        if (title != null && title.contains("策略")) {
            SystemRing.getInstance().play(0);
            Intent intent = new Intent(JetpackTestActivity.ACTION_NOTICE);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            sendBroadcast(intent);
        }


    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        // TODO Auto-generated method stub
        Bundle extras = sbn.getNotification().extras;
        // 获取接收消息APP的包名
        String notificationPkg = sbn.getPackageName();
        // 获取接收消息的抬头
        String title = extras.getString(Notification.EXTRA_TITLE);
        // 获取接收消息的内容
        String content = extras.getString(Notification.EXTRA_TEXT);
        LogUtil.i("清除消息 ==== 消息标题: " + title + " content: " + content + "  :" + notificationPkg);
        if (title != null && title.contains("策略")) {
            SystemRing.getInstance().stop(0);
        }
    }
}

