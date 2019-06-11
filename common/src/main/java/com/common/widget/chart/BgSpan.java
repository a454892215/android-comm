package com.common.widget.chart;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;

import androidx.annotation.NonNull;


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

    private BgSpan(int textColor, int bgColor) {
        super();
        this.textColor = textColor;
        this.bgColor = bgColor;

    }

    static BgSpan getSpan(int bgColor, int textColor) {
        return new BgSpan(bgColor, textColor);
    }

    BgSpan setPadding(float verticalPadding, float horizontalPadding) {
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;
        return this;
    }

    BgSpan setRectRadius(float rectRadius) {
        this.rectRadius = rectRadius;
        return this;
    }


    BgSpan setStrokeWidth(float strokeWidth) {
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

       /* if (size != 0) {
            float textCX = x + textWidth - textWidth / 2f;
            float textCY = bottom / 2f;
            canvas.drawRoundRect(textCX - size / 2f, textCY - size / 2f,
                    textCX + size / 2f, textCY + size / 2f, rectRadius, rectRadius, paint);
        }*/
        paint.setColor(this.textColor);
        paint.setStrokeWidth(originStrokeWidth);
        canvas.drawText(text, start, end, x, y, paint);
        paint.setColor(originColor);
    }
}
