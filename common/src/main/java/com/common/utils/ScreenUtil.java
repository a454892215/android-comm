package com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;

/**
 * Description: 屏幕工具类（状态栏、截图、width、height）
 */

public class ScreenUtil {
    /**
     * dp 转化为 px
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp 转化为 px
     */
    public static float sp2px(Context context, float spVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px 转化为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度高度
     */
    public static Point getWidthHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point;
    }

    /**
     * 获取设备宽度（px）
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }


    /**
     * 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     */
    public static float getDensity(Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.density;
    }

    /**
     * @param activity
     * @return 屏幕密度（每寸像素：120/160/240/320）
     */
    public static int getDensityDPI(Activity activity) {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        return dm.densityDpi;
    }

    /**
     * 状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;//应用区域的上边沿
    }

    /**
     * 标题栏高度
     *
     * @param activity
     * @return
     */
    public static int getTitleBarHeight(Activity activity) {
        View v = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
        return v.getTop() - getStatusBarHeight(activity);//标题栏高度 = View绘制区域的上边沿-状态栏高度
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     * 截图后顶部有白色的状态栏，如果不想要，可以通过snapShotWithoutStatusBar获取截图
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShot(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        int width = getScreenWidth(activity);
        int height = getScreenHeight(activity);
        Bitmap bp = null;
        bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return bp;

    }

}

