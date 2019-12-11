package com.test.util;

import android.os.Bundle;
import android.widget.ImageView;

import com.common.comm.L;
import com.common.utils.QRCodeUtils;
import com.test.util.base.BaseAppActivity;


public class QRCodeTestActivity extends BaseAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv_qr = findViewById(R.id.iv_qr);
        int size = Math.round(L.dp_1 * 300);
        iv_qr.setImageBitmap(QRCodeUtils.createQRCodeBitmap("https://www.baidu.com", size, size));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }
}
