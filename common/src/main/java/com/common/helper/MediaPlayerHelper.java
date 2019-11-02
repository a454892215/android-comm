package com.common.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.common.utils.SharedPreUtils;

public class MediaPlayerHelper {

    private MediaPlayer mediaPlayer;
    private static final String KEY_VOLUME_SIZE_BG = "KEY_VOLUME_SIZE_BG";

    /**
     * @param context    context
     * @param resourceId 例如 R.raw.music_game
     */
    public void init(Context context, int resourceId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceId);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(true);
            float volume = SharedPreUtils.getFloat(KEY_VOLUME_SIZE_BG, 0.5f);
            mediaPlayer.setVolume(volume, volume);
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
        }
    }
}
