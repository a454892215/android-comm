package com.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.common.R;

/**
 * Author: Pan
 * Description:
 */
public abstract class BasePopWindow extends PopupWindow {
    private final Activity activity;

    public abstract int getLayoutId();

    public BasePopWindow(Context context, Activity activity, boolean outsideTouchable) {
        super(context);
        this.activity = activity;
        init(activity, outsideTouchable);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Activity activity, boolean outsideTouchable) {
        View contentView = LayoutInflater.from(activity).inflate(getLayoutId(), null, false);
        setContentView(contentView);
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#01ffffff")));
        this.setOutsideTouchable(outsideTouchable);
        this.setFocusable(outsideTouchable);
        this.setClippingEnabled(false);
        this.setAnimationStyle(R.style.pop_anim_style);
        this.setOnDismissListener(this::dismiss);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        if (outsideTouchable) {
            this.setTouchInterceptor((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    this.dismiss();
                    return true;
                }
                return false;
            });
        }
    }


    public void show() {
        super.showAsDropDown(activity.getWindow().getDecorView());
    }
}
