package com.common.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.base.BaseActivity;
import com.common.listener.OnRequestPermissionFinish;

import java.io.File;
import java.io.FileOutputStream;

@SuppressWarnings("unused")
public class ImgUtils {

    private BaseActivity activity;

    private boolean isOnlySaveInPictureFolder;

    public ImgUtils(BaseActivity activity, boolean isOnlySaveInPictureFolder) {
        this.activity = activity;
        this.isOnlySaveInPictureFolder = isOnlySaveInPictureFolder;
    }

    public void setOnlySaveInPictureFolder(boolean onlySaveInPictureFolder) {
        isOnlySaveInPictureFolder = onlySaveInPictureFolder;
    }

    /**
     * @return 1 表示成功 -1 表示失败 0 表示等待用户授权
     */
    public int saveImageToGallery(Bitmap bmp, String fileName, OnRequestPermissionFinish onRequestPermissionFinish) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return saveImageToGallery2(bmp, fileName);
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
                activity.setOnRequestPermissionFinish(onRequestPermissionFinish);
            }
        } else {
            return saveImageToGallery2(bmp, fileName);
        }
        return 0;
    }

    /**
     * @return 1 表示成功 -1 表示失败
     * 保存在Picture目录或者指定目录
     */
    public int saveImageToGallery2(Bitmap bmp, String fileName) {
        try {
            if (isOnlySaveInPictureFolder) {
                MediaStore.Images.Media.insertImage(activity.getContentResolver(), bmp, fileName, "pic");
            } else {
                String path = "comm_photo";
                File appDir = new File(Environment.getExternalStorageDirectory(), path);
                if (!appDir.exists()) {
                    boolean mkdir = appDir.mkdir();
                    LogUtil.d("创建图片目录是否成功：" + mkdir);
                }
                File imgFile = new File(appDir, fileName);
                FileOutputStream fos = new FileOutputStream(imgFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                String imgAbsolutePath = imgFile.getAbsolutePath();
                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + imgAbsolutePath)));
                LogUtil.d("============:文件保存目录：" + imgAbsolutePath);
            }
            return 1;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return -1;
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
}
