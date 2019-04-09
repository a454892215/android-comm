package com.common.widget.table;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ReplacementSpan;

/**
 * Author:  L
 * CreateDate: 2019/1/7 9:15
 * Description: No
 */

public class BgSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int textWidth;
    private float radius;
    private float verticalPadding;
    private float horizontalPadding;
    private float size; //矩形的宽高
    private float rectRadius;
    private boolean isStroke = false;
    private float strokeWidth;

    public BgSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.size = size;
        this.rectRadius = rectRadius;
    }

    public BgSpan(int bgColor, int textColor, float radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.radius = radius;
    }

    public static SpannableStringBuilder getStrokeSpan(String text, int bgColor, int textColor, float verPadding, float horPadding, float rectRadius, float strokeWidth) {
        BgSpan bgSpan = new BgSpan(bgColor, textColor)
                .setPadding(verPadding, horPadding)
                .setRectRadius(rectRadius)
                .setStrokeWidth(strokeWidth);
        text = text + " ";
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(bgSpan, 0, text.length() - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return builder;
    }

    @SuppressWarnings("unused")
    public BgSpan setPadding(float verticalPadding, float horizontalPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    @SuppressWarnings("unused")
    public BgSpan setRoundRect(int size, float rectRadius) {
        this.size = size;
        this.rectRadius = rectRadius;
        return this;
    }

    @SuppressWarnings("unused")
    public BgSpan setRectRadius(float rectRadius) {
        this.rectRadius = rectRadius;
        return this;
    }


    @SuppressWarnings("unused")
    public BgSpan setStrokeWidth(float strokeWidth) {
        this.isStroke = true;
        this.strokeWidth = strokeWidth;
        return this;
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        textWidth = Math.round(paint.measureText(text, start, end));
        return textWidth;
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        int originColor = paint.getColor();
        paint.setColor(this.bgColor);
        //  LogUtil.d("text:" + text + " start: " + start + "  end:" + end + "  x:" + x + "  y:" + y + "  top:" + top + "  bottom:" + bottom);
        float originStrokeWidth = paint.getStrokeWidth();
        if (isStroke) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(this.strokeWidth);
        } else {
            paint.setStyle(Paint.Style.FILL);
        }

        if (radius == 0) {
            if (horizontalPadding != 0 || verticalPadding != 0) {
                canvas.drawRoundRect(x - horizontalPadding, top - verticalPadding,
                        x + textWidth + horizontalPadding, bottom + verticalPadding, rectRadius, rectRadius, paint);
            } else if (size != 0) {
                float textCX = x + textWidth - textWidth / 2f;
                float textCY = bottom / 2f;
                canvas.drawRoundRect(textCX - size / 2f, textCY - size / 2f,
                        textCX + size / 2f, textCY + size / 2f, rectRadius, rectRadius, paint);
            }
        } else {
            canvas.drawCircle(x + textWidth / 2f, bottom / 2f, radius, paint);
        }

        paint.setColor(this.textColor);
        paint.setStrokeWidth(originStrokeWidth);
        canvas.drawText(text, start, end, x, y, paint);
        paint.setColor(originColor);
    }
}
