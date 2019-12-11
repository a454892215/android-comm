package com.test.util;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.common.utils.ImgUtils;
import com.common.utils.QRCodeUtils;
import com.common.utils.ToastUtil;
import com.test.util.base.BaseAppActivity;


public class QRCodeTestActivity extends BaseAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv_qr = findViewById(R.id.iv_qr);
        iv_qr.post(() -> {
            iv_qr.setImageBitmap(QRCodeUtils.createQRCodeBitmap("https://www.baidu.com", iv_qr.getWidth(), iv_qr.getHeight()));
            ImgUtils imgUtils = new ImgUtils(this, true);
            iv_qr.setOnClickListener(v -> {
                Bitmap bitmapFromView = ImgUtils.loadBitmapFromView(iv_qr);
                String fileName = "test_9999.jpg";
                int state = imgUtils.saveImageToGallery(bitmapFromView, fileName, allow -> {
                    if (allow) {
                        int state_2 = imgUtils.saveImageToGallery2(bitmapFromView, fileName);
                        onImgSaveFinish(state_2);
                    } else {
                        ToastUtil.showShort("请允许储存权限");
                    }
                });
                onImgSaveFinish(state);
            });
        });


    }

    private void onImgSaveFinish(int state_2) {
        if (state_2 == 1) {
            ToastUtil.showShort("图片保存成功");
        } else if (state_2 == -1) {
            ToastUtil.showShort("图片保存失败");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }
}
