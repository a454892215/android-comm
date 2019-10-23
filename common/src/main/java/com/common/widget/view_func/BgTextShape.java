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

public class BgTextShape {
    private final Paint bgPaint;
    private final Paint textPaint;
    private View view;
    private float radius;
    private float width;
    private float height;


    private boolean isStroke = false;
    private final RectF rect;
    private int color;
    private String text;

    private float top;
    private float left;

    public BgTextShape(float width, float height, float radius, int color, String text, int textColor, float textSize) {
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.color = color;
        bgPaint = new TextPaint();
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);

        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        rect = new RectF();

        this.text = text;
    }


    public void onDraw(Canvas canvas) {
        if (isStroke) {
            bgPaint.setStyle(Paint.Style.STROKE);
        } else {
            bgPaint.setStyle(Paint.Style.FILL);
        }
        bgPaint.setColor(color);
        rect.left = view.getWidth() / 2f - width / 2f + left; //默认shape 绘制在中心
        rect.right = rect.left + width;
        rect.top = view.getHeight() / 2f - height / 2f + top;
        rect.bottom = rect.top + height;

        canvas.drawRoundRect(rect, radius, radius, bgPaint);
        float centerX = rect.left + (rect.right - rect.left) / 2f;
        float centerY = rect.bottom - (rect.bottom - rect.top) / 2f;
        canvas.drawText(text, centerX, getBaseLine(textPaint, centerY), textPaint);
    }

    private float getBaseLine(Paint paint, float centerY) {
        return centerY - (paint.getFontMetricsInt().ascent + paint.getFontMetricsInt().descent) / 2f;
    }


    public void setStroke(boolean stroke) {
        isStroke = stroke;
    }


    public void setTop(float top) {
        this.top = top;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setView(View view) {
        this.view = view;
    }

}
