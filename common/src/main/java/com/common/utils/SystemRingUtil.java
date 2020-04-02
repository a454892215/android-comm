package com.common.utils;

import android.app.Application;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;

import java.util.ArrayList;

/**
 * Author: Pan
 * 2020/3/16
 * Description:
 */
public class SystemRingUtil {

    private static SystemRingUtil systemRing = new SystemRingUtil();

    private SystemRingUtil() {
    }

    public static SystemRingUtil getInstance() {
        return systemRing;
    }

    private ArrayList<Ringtone> ringToneList = new ArrayList<>();
    private int currentIndex = -1;
    private Ringtone currentRingtone;

    private void checkInit(Application context) {
        if (ringToneList.size() == 0) {
            try {
                RingtoneManager ringtoneManager = new RingtoneManager(context); // 铃声管理器
                Cursor cursor = ringtoneManager.getCursor(); //获取铃声表,根据表名取值
                int count = cursor.getCount(); //获取铃声列表数量
                for (int i = 0; i < count; i++) {
                    Ringtone ringtone = ringtoneManager.getRingtone(i);
                    LogUtil.d("========铃声:" + ringtone.getTitle(context) + " index ：" + i);
                    ringToneList.add(ringtone);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }

    }

    public void play(Application context, int index) {
        checkInit(context);
        stopRecentRing();
        Ringtone ringtone = ringToneList.get(index);
        ringtone.play();
        currentRingtone = ringtone;
    }

    public void playNext(Application context) {
        checkInit(context);
        currentIndex = MathUtil.clamp(++currentIndex, 0, ringToneList.size() - 1);
        stopRecentRing();
        Ringtone ringtone = ringToneList.get(currentIndex);
        ringtone.play();
        currentRingtone = ringtone;
    }

    public void playLast(Application context) {
        checkInit(context);
        currentIndex = MathUtil.clamp(--currentIndex, 0, ringToneList.size() - 1);
        stopRecentRing();
        Ringtone ringtone = ringToneList.get(currentIndex);
        ringtone.play();
        currentRingtone = ringtone;
    }

    public void stopRecentRing() {
        if (currentRingtone != null && currentRingtone.isPlaying()) {
            currentRingtone.stop();
        }
    }

    public void playOrStopRecentRing(Application app, int index) {
        if (currentRingtone != null && currentRingtone.isPlaying()) {
            currentRingtone.stop();
        } else {
            play(app, index);
        }
    }

}
