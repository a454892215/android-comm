package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

import com.common.R;


/**
 * Author:  Pan
 * CreateDate: 2019/7/23 11:40
 * Description: No
 */

public class BottomText {
    private final TextPaint textPaint;
    private String text = "";
    private View view;
    private int unselectedColor, selectedColor;
    private float top;
    private boolean isUseSelectColor = false;

    public BottomText(View view, int color, float textSize) {
        this.view = view;
        top = -3 * view.getResources().getDimension(R.dimen.dp_1);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(color);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);
    }

    public BottomText(View view, float textSize, int unselectedColor, int selectedColor) {
        this.view = view;
        top = -3 * view.getResources().getDimension(R.dimen.dp_1);
        textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(textSize);

        isUseSelectColor = true;
        this.unselectedColor = unselectedColor;
        this.selectedColor = selectedColor;
    }

    public void onDraw(Canvas canvas) {
        if (isUseSelectColor) {
            textPaint.setColor(view.isSelected() ? selectedColor : unselectedColor);
        }
        int textHeight = textPaint.getFontMetricsInt().descent - textPaint.getFontMetricsInt().ascent;
        float drawingTextCenterY = view.getHeight() - textHeight / 2f + top;
        String drawingText = text == null ? "" : text;
        canvas.drawText(drawingText, view.getWidth() / 2f, getBaseLine(textPaint, drawingTextCenterY), textPaint);
    }

    private float getBaseLine(Paint paint, float centerY) {
        return centerY - (paint.getFontMetricsInt().ascent + paint.getFontMetricsInt().descent) / 2f;
    }

    public void setText(String text) {
        this.text = text;
        view.invalidate();
    }

    public String getText() {
        return text;
    }

}
