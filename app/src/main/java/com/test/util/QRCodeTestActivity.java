package com.test.util;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.common.comm.L;
import com.common.utils.ImgUtils;
import com.common.utils.QRCodeUtils;
import com.common.utils.ToastUtil;
import com.test.util.base.BaseAppActivity;



public class QRCodeTestActivity extends BaseAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv_qr = findViewById(R.id.iv_qr);
        int size = Math.round(L.dp_1 * 300);
        iv_qr.setImageBitmap(QRCodeUtils.createQRCodeBitmap("https://www.baidu.com", size, size));
        iv_qr.setOnClickListener(v -> {
            Bitmap bitmapFromView = ImgUtils.loadBitmapFromView(iv_qr);
           // String dateFileName = DateUtil.getDateFileName(new Date());
            if (ImgUtils.saveImageToGallery(activity, bitmapFromView, "test_12345.jpg")) {
                ToastUtil.showShort("图片保存成功");
            } else {
                ToastUtil.showShort("图片保存失败");
            }

        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }
}
