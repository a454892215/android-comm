package com.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import com.common.CommApp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("unused")
public class AssertsUtil {

    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap bitmap = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getThumbnailBitmapFromAssetsFile(Context context, String fileName, int width, int height) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            AssetManager am = context.getResources().getAssets();
            InputStream is = am.open(fileName);
            // 获取这个图片的宽和高，注意此处的bitmap为null
            bitmap = BitmapFactory.decodeStream(is, null, options);
            options.inJustDecodeBounds = false; // 设为 false
            // 计算缩放比
            int h = options.outHeight;
            int w = options.outWidth;
            int beWidth = w / width;
            int beHeight = h / height;
            int be;
            if (beWidth < beHeight) {
                be = beWidth;
            } else {
                be = beHeight;
            }
            if (be <= 0) {
                be = 1;
            }
            is.reset();//模拟器返回为NULL的bug
            options.inSampleSize = be;
            bitmap = BitmapFactory.decodeStream(is, null, options);//保持原图比例
            // LogUtils.d("bitmap:"+bitmap);
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//不保持原图比例，返回参数指定尺寸
            // LogUtils.d("2.这里的bitmaps的大小是："+bitmap.getWidth()+":"+bitmap.getHeight());
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("处理assert目录的缩略图出现异常:" + e.toString());
        }
        return bitmap;
    }

    private Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static String getText(String fileName) {
        String text = "";
        try {
            InputStream in = CommApp.app.getAssets().open(fileName);
            int available = in.available();
            byte[] buff = new byte[available];
            int read = in.read(buff);
            if (read > 0) {
                text = new String(buff, StandardCharsets.UTF_8);
            } else {
                LogUtil.e("读取asserts资源异常");
            }
        } catch (IOException e) {
            LogUtil.e("读取asserts资源异常");
            e.printStackTrace();
        }
        return text;
    }
}
