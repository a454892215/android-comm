package com.test.util.network;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.common.GlideApp;
import com.common.http.ApiCreator;
import com.common.http.HttpUtil;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.test.util.BuildConfig;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;

public class HttpTestActivity extends BaseAppActivity {

    private static GateApi gateApi;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (gateApi == null) {
            ApiCreator apiCreator = new ApiCreator();
            apiCreator.logEnable(BuildConfig.DEBUG);
            gateApi = apiCreator.getApi(GateApi.baseUrl, GateApi.class);
        }

        httpUtil = new HttpUtil(this);
        httpUtil.showLoadingEnable(true);
        findViewById(R.id.btn).setOnClickListener(v -> requestData());
        ImageView img_test = findViewById(R.id.img_test);
        String url = "https://img.ivsky.com/img/tupian/pre/201811/07/pubu-006.jpg";
        GlideApp.with(activity).load(url).into(img_test);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_enter;
    }


    private void requestData() {
        httpUtil.requestData(gateApi.getCandleStick2(), new HttpCallback() {
            @Override
            public void onSuccess(String text) {
                LogUtil.d("===================text:" + text);
                ToastUtil.showShort(activity, "请求成功：" + text);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
