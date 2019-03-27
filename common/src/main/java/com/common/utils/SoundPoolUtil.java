package com.common.utils;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.common.base.BaseActivity;
import com.common.comm.MyCountDownTimer;

import java.util.ArrayList;
import java.util.List;

public class SoundPoolUtil {
    private SoundPool soundPool;
    private Context context;

    private List<Integer> loadedIdList = new ArrayList<>();

    public SoundPoolUtil(Context context, int... ids) {
        this.context = context;
        AudioAttributes audioAttributes = new AudioAttributes.Builder().
                setLegacyStreamType(AudioManager.STREAM_MUSIC).build();
        soundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(3).build();
        for (int id : ids) {
            int soundId = soundPool.load(context, id, 1);
            if (soundId != 0) {
                loadedIdList.add(soundId);
            } else {
                LogUtil.e("加载音频资源失败：");
            }
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    private float volume = 0.5f;

    public void play(int position) {
        soundPool.play(loadedIdList.get(position), volume, volume, 0, 0, 1);
    }


    public void playOnlyOne(int resId, int playTime, BaseActivity activity) {
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            int streamID = soundPool.play(sampleId, volume, volume, 1, 0, 1);
            activity.setOnPauseListener(() -> soundPool.stop(streamID));
            MyCountDownTimer timer = new MyCountDownTimer(playTime + 2, 1000);
            timer.setOnLastTickListener((time, count) -> soundPool.unload(sampleId));
            timer.start();
        });
        soundPool.load(context, resId, 1);
    }

}
