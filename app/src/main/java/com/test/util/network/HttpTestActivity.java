package com.test.util.network;

import android.os.Bundle;
import android.widget.ImageView;

import com.common.http.ApiCreator;
import com.common.http.HttpUtil;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.test.util.BuildConfig;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

public class HttpTestActivity extends MyBaseActivity {

    private static OkexApi api;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (api == null) {
            ApiCreator apiCreator = new ApiCreator();
            apiCreator.logEnable(BuildConfig.IS_DEBUG);
            api = apiCreator.getApi(OkexApi.baseUrl, OkexApi.class);
        }

        httpUtil = new HttpUtil(this);
        httpUtil.showLoadingEnable(true);
        findViewById(R.id.btn).setOnClickListener(v -> requestData());
        requestData();
        ImageView img_test = findViewById(R.id.img_test);
        String url = "https://img.ivsky.com/img/tupian/pre/201811/07/pubu-006.jpg";
       // GlideApp.with(activity).load(url).into(img_test);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_enter;
    }


    private void requestData() {
        httpUtil.requestData(api.getBtcCandle_old(), new HttpCallback() {
            @Override
            public void onSuccess(String text) {

            }

            @Override
            public void onFail(Throwable e) {
                LogUtil.e(e);
            }
        });
    }
}
