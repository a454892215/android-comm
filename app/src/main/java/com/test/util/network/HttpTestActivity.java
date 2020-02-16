package com.test.util.network;

import android.os.Bundle;
import android.widget.ImageView;

import com.common.GlideApp;
import com.common.helper.GsonHelper;
import com.common.http.ApiCreator;
import com.common.http.HttpUtil;
import com.common.http.inter.HttpCallback;
import com.common.utils.LogUtil;
import com.test.util.R;
import com.test.util.base.BaseAppActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HttpTestActivity extends BaseAppActivity {

    private static OkexApi api;
    private HttpUtil httpUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (api == null) {
            ApiCreator apiCreator = new ApiCreator();
            apiCreator.logEnable(false);
            api = apiCreator.getApi(OkexApi.baseUrl, OkexApi.class);
        }

        httpUtil = new HttpUtil(this);
        httpUtil.showLoadingEnable(true);
        findViewById(R.id.btn).setOnClickListener(v -> requestData());
        requestData();
        ImageView img_test = findViewById(R.id.img_test);
        String url = "https://img.ivsky.com/img/tupian/pre/201811/07/pubu-006.jpg";
        GlideApp.with(activity).load(url).into(img_test);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin_enter;
    }


    private void requestData() {
        httpUtil.requestData(api.getBtcCandle(), new HttpCallback() {
            @Override
            public void onSuccess(String text) {
                OkCandleEntity entity = GsonHelper.getEntity(text, OkCandleEntity.class);
                int size = entity.data.size();
                for (int i = 0; i < size; i++) {
                    List<String> itemList = entity.data.get(i);
                    String date_str = itemList.get(0).substring(0, 10);
                    LogUtil.d("     i:" + i + "  date:" + date_str);

                    Calendar calendar = Calendar.getInstance();
                }

            }

            @Override
            public void onFail(Throwable e) {
                LogUtil.e(e);
            }
        });
    }
}
