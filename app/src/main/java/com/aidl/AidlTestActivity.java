package com.aidl;

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
        findViewById(R.id.btn_test).setOnClickListener(v -> {
            Intent intent = new Intent(this, AidlTestActivity.class);
            bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder binder) {
                    try {
                        IMyAidlInterface aidlInterface = (IMyAidlInterface) binder;
                        LogUtil.d(aidlInterface.getValue(22) + "");
                        LogUtil.d(aidlInterface.getName("getName-"));
                        aidlInterface.test("gaga111");
                    } catch (Exception e) {
                        LogUtil.e(e);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, Context.BIND_AUTO_CREATE);
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_aidl_test;
    }
}
