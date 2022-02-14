package com.aidl;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.common.utils.LogUtil;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

public class AidlTestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.btn_test1).setOnClickListener(v -> toBindService(AidlService.class));
        findViewById(R.id.btn_test2).setOnClickListener(v -> toBindService(AidlService2.class));
        findViewById(R.id.btn_test3).setOnClickListener(v -> toBindService(AidlService3.class));
    }

    private void toBindService(Class<? extends Service> service) {
        LogUtil.d("准备绑定服务:" + service.getSimpleName());
        Intent intent = new Intent(this, service);
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                try {
                    LogUtil.d("service 已连接：");
                    IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(binder);
                    iMyAidlInterface.test(null);
                    LogUtil.d("list大小：" + iMyAidlInterface.getValue(0));
                } catch (Exception e) {
                    LogUtil.e(e);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtil.d("service 服务连接断开：");
            }
        }, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_aidl_test;
    }
}
