package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.view.View;


/**
 * Author:  Pan
 * CreateDate: 2019/7/23 11:40
 * Description: No
 */

public class BgShape {
    private final Paint paint;
    private View view;
    private float radius;
    private float width;
    private float height;
    private boolean isStroke;
    private final RectF rect;
    private int color;

    public BgShape(View view, float width, float height, float radius, int color, boolean isStroke) {
        this.view = view;
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isStroke = isStroke;
        paint = new TextPaint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        rect = new RectF();
    }


    public void onDraw(Canvas canvas) {
        if (isStroke) {
            paint.setStyle(Paint.Style.STROKE);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }
        paint.setColor(color);
        rect.left = view.getWidth() / 2f - width / 2f;
        rect.right = rect.left + width;
        rect.top = view.getHeight() / 2f - height / 2f;
        rect.bottom = rect.top + height;

        canvas.drawRoundRect(rect, radius, radius, paint);
    }

    public void updateBgShape(float width, float height, float radius, int color, boolean isStroke) {
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.color = color;
        this.isStroke = isStroke;
        view.invalidate();
    }

}
