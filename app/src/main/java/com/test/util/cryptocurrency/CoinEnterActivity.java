package com.test.util.cryptocurrency;

import android.os.Bundle;
import android.widget.Button;

import com.common.http.ApiCreator;
import com.common.http.HttpUtil;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.test.util.BuildConfig;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;

public class CoinEnterActivity extends BaseAppActivity {

    private static Api gateApi;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (gateApi == null) {
            ApiCreator apiCreator = new ApiCreator();
            apiCreator.logEnable(BuildConfig.DEBUG);
            gateApi = apiCreator.getApi(Api.baseUrl, Api.class);
        }

        httpUtil = new HttpUtil(this);
        requestData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_enter;
    }


    private void requestData() {
        httpUtil.requestData(gateApi.getPairs(), new HttpCallback() {
            @Override
            public void onSuccess(String text) {
                LogUtil.d("===================text:" + text);
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
