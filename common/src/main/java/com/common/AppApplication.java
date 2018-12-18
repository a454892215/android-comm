package com.common;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author:  Pan
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
    }

    private void initLogger() {
        PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
        builder.showThreadInfo(false).methodCount(2).methodOffset(1) ;
        PrettyFormatStrategy build = builder.build();
        Logger.addLogAdapter(new AndroidLogAdapter(build));
    }
}
