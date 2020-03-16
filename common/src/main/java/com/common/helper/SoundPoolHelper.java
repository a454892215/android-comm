package com.common.helper;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;

import com.common.base.BaseActivity;
import com.common.comm.timer.MyTimer;
import com.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SoundPoolHelper {
    private SoundPool soundPool;
    private Context context;

    private List<Integer> loadedIdList = new ArrayList<>();

    public SoundPoolHelper(Context context, int... ids) {
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

    public void repeatPlay(int position) {
        soundPool.play(loadedIdList.get(position), volume, volume, 0, -1, 1);
    }

    public void stop(int position) {
        soundPool.stop(loadedIdList.get(position));
    }

    /**
     * 示例 soundPoolUtil.playOnlyOne(R.raw.music_activity, 4, activity)
     *
     * @param resId    R.raw.music_aty
     * @param playTime 4
     * @param activity activity
     */
    public void playOnlyOne(int resId, int playTime, BaseActivity activity) {
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            int streamID = soundPool.play(sampleId, volume, volume, 1, 0, 1);
            activity.addOnPauseListener(() -> soundPool.stop(streamID));
            MyTimer timer = new MyTimer(playTime * 1000 + 2000, 1000);
            timer.setOnTickListener((time, count) -> {
                if (time == 0) {
                    soundPool.unload(sampleId);
                }
            });
            timer.start();
        });
        soundPool.load(context, resId, 1);
    }

}
