package com.common.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Description: Toast工具类
 */

public class ToastUtil {

    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    public static void showShort(Context context, CharSequence message) {
        if (!isMainThread()) {
            Observable.just(1).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        showShortToast(context, message);
                    });
            return;
        }
        showShortToast(context, message);
    }

    public static void showShort(Context context, int resId) {
        if (!isMainThread()) {
            Observable.just(1).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        showShortToast(context, resId);
                    });
            return;
        }
        showShortToast(context, resId);
    }


    public static void showLong(Context context, CharSequence message) {
        if (!isMainThread()) {
            Observable.just(1).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        showLongToast(context, message);
                    });
            return;
        }
        showLongToast(context, message);
    }

    public static void showLong(Context context, int resId) {
        if (!isMainThread()) {
            Observable.just(1).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        showLongToast(context, resId);
                    });
            return;
        }
        showLongToast(context, resId);
    }

    private static void showShortToast(Context context, CharSequence message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showShortToast(Context context, int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showLongToast(Context context, CharSequence message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private static void showLongToast(Context context, int resId) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void show(Context context, String msg) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.layout_toast_text);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }
}
