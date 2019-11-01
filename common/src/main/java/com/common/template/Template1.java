package com.common.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;

import com.common.utils.ImgUtils;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;

@SuppressWarnings("unused")
public class Template1 {

    /**
     * WebView 加载html格式的文本
     */
    public static void tem1(Context context) {
        WebView web_view = new WebView(context);
        web_view.setBackgroundColor(Color.parseColor("#000000")); //黑色背景
        web_view.loadDataWithBaseURL(null, "html格式的数据", "text/html", "UTF-8", null);
    }

    public static void savePicToLocal(Activity context, ImageView iv_qr_code) {
        try {
            ActivityCompat.requestPermissions(context, new String[]{android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            iv_qr_code.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(iv_qr_code.getDrawingCache(false));
            iv_qr_code.setDrawingCacheEnabled(false);
            if (bitmap != null) {
                boolean isOk = ImgUtils.saveImageToGallery(context, bitmap);
                if (isOk) {
                    ToastUtil.showShort(context, "保存二维码成功");
                }
            } else {
                ToastUtil.showShort(context, "保存二维码失败");
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
