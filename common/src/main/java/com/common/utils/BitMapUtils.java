package com.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * author: ${VenRen}
 * created on: 2019/2/26 21:35
 * description: bitmap 工具类
 */
public class BitMapUtils {

    /**
     * 将字符串转换成Bitmap类型
     */
    public static Bitmap stringToBitmap(String stringBitmap) {

        Bitmap bitmap = null;
        try {
            byte[] bytes = Base64.decode(stringBitmap, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
