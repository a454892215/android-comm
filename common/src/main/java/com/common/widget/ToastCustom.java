package com.common.widget;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.AppApplication;
import com.common.R;

/**
 * author: ${VenRen}
 * created on: 2019/2/22 10:48
 * description: 自定义Toast
 */
public class ToastCustom {
    private static Toast sToast;
    private ImageView sToastStatus;
    private TextView sMessage;

    private ToastCustom() {
        sToast = new Toast(AppApplication.sContext);
        sToast.setGravity(Gravity.CENTER, 0, 0);
        View toastRoot = LayoutInflater.from(AppApplication.sContext).inflate(R.layout.layout_toast, null);
        sToastStatus = toastRoot.findViewById(R.id.image_toast_status);
        sMessage = toastRoot.findViewById(R.id.text_toast_message);
        sToast.setView(toastRoot);
    }


    public static synchronized ToastCustom getInstance() {
        return new ToastCustom();
    }

    /**
     * 默认提供两种状态
     *
     * @param isSuccess 确定状态
     * @param message   信息
     */
    public void showShort(boolean isSuccess, String message) {
        setToastInfo(isSuccess, message);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }

    /**
     * 传入Drawable
     *
     * @param drawable 左边图片
     * @param message  信息
     */
    public void showShort(int drawable, String message) {
        setToastInfo(drawable, message);
        sToast.setDuration(Toast.LENGTH_SHORT);
        sToast.show();
    }

    /**
     * 默认提供两种状态
     *
     * @param isSuccess 确定状态
     * @param message   信息
     */
    public void showLong(boolean isSuccess, String message) {
        setToastInfo(isSuccess, message);
        sToast.setDuration(Toast.LENGTH_LONG);
        sToast.show();
    }

    /**
     * 默认提供两种状态
     *
     * @param drawable 左边图片
     * @param message  信息
     */
    public void showLong(int drawable, String message) {
        setToastInfo(drawable, message);
        sToast.setDuration(Toast.LENGTH_LONG);
        sToast.show();
    }

    private void setToastInfo(boolean isSuccess, String message) {
        Drawable drawable;
        if (isSuccess) {
            drawable = ResourcesCompat.getDrawable(AppApplication.sContext.getResources(), R.drawable.icon_toast_success, null);
        } else {
            drawable = ResourcesCompat.getDrawable(AppApplication.sContext.getResources(), R.drawable.icon_toast_error, null);
        }
        sToastStatus.setImageDrawable(drawable);
        sMessage.setText(message);
    }

    private void setToastInfo(int drawable, String message) {
        sToastStatus.setImageResource(drawable);
        sMessage.setText(message);
    }
}
