package com.common.utils;

import android.content.Context;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;

import com.common.CommApp;

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

    public void init(Context context) {
        ringToneList.clear();
        RingtoneManager ringtoneManager = new RingtoneManager(context); // 铃声管理器
        Cursor cursor = ringtoneManager.getCursor(); //获取铃声表,根据表名取值
        int count = cursor.getCount(); //获取铃声列表数量
        for (int i = 0; i < count; i++) {
            Ringtone ringtone = ringtoneManager.getRingtone(i);
            LogUtil.d("========铃声:" + ringtone.getTitle(context) + " index ：" + i);
            ringToneList.add(ringtone);
        }
    }

    public void play(int index) {
        Ringtone ringtone = ringToneList.get(index);
        if (!ringtone.isPlaying()) {//不在播放状态
            stopRecentRing();
            ringtone.play();
            currentRingtone = ringtone;
        }
    }

    public void stop(int index) {
        Ringtone ringtone = ringToneList.get(index);
        if (ringtone.isPlaying()) {//不在播放状态
            ringtone.stop();
        }
    }

    public void playNext() {
        currentIndex = MathUtil.clamp(++currentIndex, 0, ringToneList.size() - 1);
        Ringtone ringtone = ringToneList.get(currentIndex);
        LogUtil.d("================playNext:" + currentIndex + "  :title:" + ringtone.getTitle(CommApp.app));
        if (!ringtone.isPlaying()) {//不在播放状态
            stopRecentRing();
            ringtone.play();
            currentRingtone = ringtone;
        }
    }

    public void playLast() {
        currentIndex = MathUtil.clamp(--currentIndex, 0, ringToneList.size() - 1);
        Ringtone ringtone = ringToneList.get(currentIndex);
        LogUtil.d("================playLast:" + currentIndex + "  :title:" + ringtone.getTitle(CommApp.app));
        if (!ringtone.isPlaying()) {//不在播放状态
            stopRecentRing();
            ringtone.play();
            currentRingtone = ringtone;
        }
    }
    
    private void stopRecentRing() {
        if (currentRingtone != null && currentRingtone.isPlaying()) {
            currentRingtone.stop();
        }
    }

}
