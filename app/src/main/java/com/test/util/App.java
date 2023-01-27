package com.test.util;

import com.common.CommApp;
import com.common.utils.LogUtil;
import com.common.helper.SoundPoolHelper;
import com.common.utils.SystemUtils;
import com.example.jpushdemo.JGInit;
import com.test.util.box_obj.ObjectBox;

/**
 * Author:  L
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class App extends CommApp {

    public SoundPoolHelper soundPoolUtil;
    public static App app;

    @Override
    public void onCreate() {
        try {
            isInitX5Web = true;
            app = this;
            super.onCreate();
            ObjectBox.INSTANCE.init(this);
            LogUtil.logEnable(BuildConfig.DEBUG);
            soundPoolUtil = new SoundPoolHelper(this, R.raw.button_tap);
            JGInit.init(this);
            if(SystemUtils.isMainProcess(this)){
                LogUtil.i("=App==onCreate===========:" + BuildConfig.app_info);
            }

        } catch (Exception e) {
            LogUtil.e(e);
        }
    }
}
