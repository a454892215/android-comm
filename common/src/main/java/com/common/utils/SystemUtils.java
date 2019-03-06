package com.common.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.common.base.BaseActivity;

/**
 * Created by dl on 2019/3/5.
 */

public class SystemUtils {


    public static boolean setClipData(String label, String text, BaseActivity activity) {
        try {
            ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(label, text);
            if (cm != null) {
                cm.setPrimaryClip(clipData);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("LLpp:" + e.toString());
            return false;
        }
    }

}
