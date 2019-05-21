package com.test.util;

import com.common.AppApplication;
import com.common.utils.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class MyAppApplication extends AppApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.logEnable(BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), "89a3be5c8c", BuildConfig.DEBUG);
    }


}
