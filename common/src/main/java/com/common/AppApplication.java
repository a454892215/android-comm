package com.common;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * Author:  Pan
 * CreateDate: 2018/12/17 16:47
 * Description: No
 */

public class AppApplication extends Application {

    public static Typeface sTypeface;  //字体样式

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();
      //  sTypeface = Typeface.createFromAsset(getAssets(), "fonts/DroidSansFallback.ttf");
    }

    private void initLogger() {
        PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
        builder.showThreadInfo(false).methodCount(2).methodOffset(1);
        PrettyFormatStrategy build = builder.build();
        Logger.addLogAdapter(new AndroidLogAdapter(build));
    }

}
