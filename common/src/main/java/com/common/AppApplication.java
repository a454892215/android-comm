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
    public static String sDevice = "android";
    public static final String TOKE_KEY = "token key";
    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        initLogger();

       // sTypeface = Typeface.createFromAsset(getAssets(), "fonts/HiraginoSansGB.otf");
        sTypeface = Typeface.createFromAsset(getAssets(), "fonts/DroidSansFallback.ttf");
        sContext = this;
    }

    private void initLogger() {
        PrettyFormatStrategy.Builder builder = PrettyFormatStrategy.newBuilder();
        builder.showThreadInfo(false).methodCount(2).methodOffset(1);
        PrettyFormatStrategy build = builder.build();
        Logger.addLogAdapter(new AndroidLogAdapter(build));
    }

}
