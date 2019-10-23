package com.common.widget.view_func;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;


/**
 * Author:  Pan
 * CreateDate: 2019/7/23 11:40
 * Description: No
 */

public class LineShape {
    private View view;
    private int lineColor;
    private float topLineStrokeWidth;
    private float rightLineStrokeWidth;

    private float bottomLineStrokeWidth;
    private float leftLineStrokeWidth;


    private Paint linePaint;

    public LineShape(View view) {
        this.view = view;
        linePaint = new TextPaint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(this.lineColor);
    }


    public void onDraw(Canvas canvas) {

        linePaint.setColor(lineColor);
        if (rightLineStrokeWidth > 0) {
            linePaint.setStrokeWidth(rightLineStrokeWidth);
            canvas.drawLine(view.getWidth() - rightLineStrokeWidth / 2, 0, view.getWidth() - rightLineStrokeWidth / 2, view.getHeight(), linePaint);
        }

        if (leftLineStrokeWidth > 0) {
            linePaint.setStrokeWidth(leftLineStrokeWidth);
            canvas.drawLine(leftLineStrokeWidth / 2, 0, leftLineStrokeWidth / 2, view.getHeight(), linePaint);
        }

        if (topLineStrokeWidth > 0) {
            linePaint.setStrokeWidth(topLineStrokeWidth);
            canvas.drawLine(0, topLineStrokeWidth / 2, view.getWidth(), topLineStrokeWidth / 2, linePaint);
        }

        if (bottomLineStrokeWidth > 0) {
            linePaint.setStrokeWidth(bottomLineStrokeWidth);
            canvas.drawLine(0, view.getHeight() - bottomLineStrokeWidth / 2, view.getWidth(), view.getHeight() - bottomLineStrokeWidth / 2, linePaint);
        }
    }

    public void setTopLineStrokeWidth(float topLineStrokeWidth) {
        this.topLineStrokeWidth = topLineStrokeWidth;
    }

    public void setRightLineStrokeWidth(float rightLineStrokeWidth) {
        this.rightLineStrokeWidth = rightLineStrokeWidth;
    }

    public void setBottomLineStrokeWidth(float bottomLineStrokeWidth) {
        this.bottomLineStrokeWidth = bottomLineStrokeWidth;
    }

    public void setLeftLineStrokeWidth(float leftLineStrokeWidth) {
        this.leftLineStrokeWidth = leftLineStrokeWidth;
    }

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

}
