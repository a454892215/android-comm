package com.common.widget.table;

import android.graphics.Canvas;
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
    private float verticalPadding;
    private float horizontalPadding;
    private float rectRadius;
    private boolean isStroke = false;
    private float strokeWidth;

    BgSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
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

    private BgSpan setPadding(float verticalPadding, float horizontalPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    private BgSpan setRectRadius(float rectRadius) {
        this.rectRadius = rectRadius;
        return this;
    }


    private BgSpan setStrokeWidth(float strokeWidth) {
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
        canvas.drawRoundRect(x - horizontalPadding, top - verticalPadding,
                x + textWidth + horizontalPadding, bottom + verticalPadding, rectRadius, rectRadius, paint);
        paint.setColor(this.textColor);
        paint.setStrokeWidth(originStrokeWidth);
        canvas.drawText(text, start, end, x, y, paint);
        paint.setColor(originColor);
    }
}
