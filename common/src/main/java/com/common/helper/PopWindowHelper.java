package com.common.helper;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.common.R;
import com.common.base.BaseActivity;

/**
 * author: Pan
 * created on: 2018/6/16 18:51
 * description:No
 */


public class PopWindowHelper {

    @SuppressLint("ClickableViewAccessibility")
    public static PopupWindow getPopWindow(View popView, int width, int height, boolean outsideTouchable) {
        final PopupWindow popupWindow = new PopupWindow(popView, width, height);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#01ffffff"));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOutsideTouchable(outsideTouchable);
        popupWindow.setFocusable(outsideTouchable);
        popupWindow.setClippingEnabled(false);
        popupWindow.setAnimationStyle(R.style.pop_anim_style);
        popupWindow.setOnDismissListener(popupWindow::dismiss);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (outsideTouchable) {
            popupWindow.setTouchInterceptor((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            });
        }
        return popupWindow;
    }

    public static void show(BaseActivity activity, PopupWindow popupWindow, int gravity) {
        if (!popupWindow.isShowing()) {
            View decorView = activity.getWindow().getDecorView();
            decorView.post(() -> popupWindow.showAtLocation(decorView, gravity, 0, BaseActivity.bottomVirtualKeyHeight));
        }
    }

    public static void show(PopupWindow popupWindow, View parent, int gravity) {
        if (!popupWindow.isShowing()) {
            parent.post(() -> popupWindow.showAtLocation(parent, gravity, 0, BaseActivity.bottomVirtualKeyHeight));
        }
    }

    public static void dismissPop(PopupWindow popupWindow) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public static void dismissPop(BaseActivity activity, PopupWindow popupWindow) {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            View decorView = activity.getWindow().getDecorView();
            decorView.postDelayed(() -> {
                if (popupWindow.isShowing()) popupWindow.dismiss();
            }, 2000);
        }
    }
}
