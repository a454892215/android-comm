package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;

import com.common.utils.ViewUtil;

public class Image {
    private View view;
    private int width;
    private int gravity;
    private final Drawable drawable;
    private final Rect rect;

    public Image(int width, int picId, int gravity, View view) {
        this.view = view;
        this.width = width;
        this.gravity = gravity;

        drawable = view.getResources().getDrawable(picId);
        int height = Math.round(ViewUtil.getPicHeightByWidth(drawable, width));
        rect = new Rect(0, 0, width, height); // 默认start
        drawable.setBounds(rect);
    }


    public void onDraw(Canvas canvas) {
        if (drawable != null) {
            if (gravity == Gravity.END) {
                rect.left = view.getWidth() - width;
                rect.right = view.getWidth();
                drawable.setBounds(rect);
            }
            drawable.draw(canvas);
        }

    }
}
