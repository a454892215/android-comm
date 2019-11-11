package com.test.util;

import com.common.AppApplication;
import com.common.utils.LogUtil;
import com.common.helper.SoundPoolHelper;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class MyAppApplication extends AppApplication {

    public SoundPoolHelper soundPoolUtil;

    @Override
    public void onCreate() {
        isInitX5Web = true;
        super.onCreate();
        LogUtil.logEnable(BuildConfig.DEBUG);
        soundPoolUtil = new SoundPoolHelper(this, R.raw.button_tap);
        LogUtil.i("==============:"+BuildConfig.app_info);
    }


}
