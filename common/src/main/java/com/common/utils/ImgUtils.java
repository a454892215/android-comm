package com.common.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ImgUtils {

    public static boolean saveImageToGallery(Activity activity, Bitmap bmp, String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return saveImageToGallery2(activity, bmp, fileName);
            } else {
                ToastUtil.showLong("请开启存储权限!!!");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            }
        } else {
            return saveImageToGallery2(activity, bmp, fileName);
        }
        return false;
    }

    private static boolean saveImageToGallery2(Context context, Bitmap bmp, String fileName) {
        try {
            String path = "comm_photo";
            // 首先保存图片
            File appDir = new File(Environment.getExternalStorageDirectory(), path);
            if (!appDir.exists()) {
                boolean mkdir = appDir.mkdir();
                LogUtil.d("创建图片目录是否成功：" + mkdir);
            }
            fileName = TextUtils.isEmpty(fileName) ? DateUtil.getDateFileName(new Date()) : fileName;
            File imgFile = new File(appDir, fileName);

            FileOutputStream fos = new FileOutputStream(imgFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            String imgAbsolutePath = imgFile.getAbsolutePath();
            MediaStore.Images.Media.insertImage(context.getContentResolver(), imgAbsolutePath, fileName, null);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imgAbsolutePath)));
            LogUtil.d("============:文件保存目录：" + imgAbsolutePath);
            return true;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return false;
    }

    /**
     * 从View中获取Bitmap
     */
    public static Bitmap loadBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas); //把View 绘制在Canvas上
        view.invalidate();
        return bitmap;
    }

    public @NonNull
    static Bitmap createBitmapFromView(@NonNull View view, int width, int height) {
        if (width > 0 && height > 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        }
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable background = view.getBackground();
        if (background != null) {
            background.draw(canvas);
        }
        view.draw(canvas);
        return bitmap;
    }
}
