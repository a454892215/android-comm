package com.common.widget.table;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.text.style.ReplacementSpan;

/**
 * Author:  L
 * CreateDate: 2019/1/7 9:15
 * Description: No
 */

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int textWidth;
    private int radius;

    public RoundBackgroundColorSpan(int bgColor, int textColor, int radius) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.radius = radius;
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

        canvas.drawCircle(x + textWidth / 2f, bottom / 2f, radius, paint);
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x, y, paint);
        paint.setColor(originColor);
    }
}
