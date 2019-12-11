package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

import com.common.comm.L;

public class Text {
    private String text;
    private View view;
    private float x;
    private float y;
    private final TextPaint textPaint;
    private Paint.Align align;

    public Text(View view, String text) {
        this.text = text;
        this.view = view;
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(0xff666666);
        textPaint.setTextSize(L.dp_1 * 13);
    }

    public Text setTextSize(float textSize) {
        textPaint.setTextSize(textSize);
        return this;
    }

    public Text setTextColor(int textColor) {
        textPaint.setColor(textColor);
        return this;
    }

    public Text setAlign(Paint.Align align) {
        this.align = align;
        textPaint.setTextAlign(align);
        return this;
    }

    public Text setX(float x) {
        this.x = x;
        return this;
    }

    public Text setY(float y) {
        this.y = y;
        return this;
    }

    public Text setText(String text) {
        this.text = text;
        view.invalidate();
        return this;
    }

    public void onDraw(Canvas canvas) {
        if (text != null) {
            String[] textArr = text.split("\\n");
            float startX = 0;
            if (align.equals(Paint.Align.CENTER)) {
                startX = view.getWidth() / 2f;
            }
            for (int i = 0; i < textArr.length; i++) {
                String s = textArr[i];
                int textHeight = textPaint.getFontMetricsInt().descent - textPaint.getFontMetricsInt().ascent;
                canvas.drawText(s, startX + x, y + textHeight * (i + 1), textPaint);
            }
        }
    }

}
