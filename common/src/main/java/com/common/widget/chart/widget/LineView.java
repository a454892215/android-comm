package com.common.widget.chart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;
import com.common.widget.chart.CoordinateEntity;

import java.util.ArrayList;
import java.util.List;



/**
 * Author:  L
 * CreateDate: 2018/8/22 15:44
 * Description: No
 */

public class LineView extends View {

    private Paint paint;
    private int line_color;
    private float lineRadius;
    private PorterDuffXfermode mode_clear;
    private int size;

    private int currentPointCount = 0; //现在的节点数目

    public LineView(Context context) {
        this(context, null);
    }

    public LineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        paint = new Paint();
        paint.setAntiAlias(true);
        line_color = Color.GRAY;
        paint.setStrokeWidth(context.getResources().getDimension(R.dimen.dp_1));
        setAlpha(0.8f);
        lineRadius = context.getResources().getDimension(R.dimen.dp_13);
        mode_clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    List<CoordinateEntity> coordinateList = new ArrayList<>();

    public void addData(List<CoordinateEntity> coordinateList) {
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
        }
        if (coordinateList != null) {
            this.coordinateList.addAll(coordinateList);
            size = this.coordinateList.size();
            currentPointCount = size;
            invalidate();
        }
    }

    public void clearData() {
        this.coordinateList.clear();
        currentPointCount = 0;
    }

    public int getCurrentPointCount() {
        return currentPointCount;
    }

    protected void onDraw(Canvas canvas) {
        paint.setXfermode(mode_clear);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        paint.setXfermode(null);
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                paint.setColor(line_color);
                canvas.drawLine(coordinateList.get(i).getX(), coordinateList.get(i).getY(),
                        coordinateList.get(i + 1).getX(), coordinateList.get(i + 1).getY(), paint);
                paint.setXfermode(mode_clear);
                canvas.drawCircle(coordinateList.get(i).getX(), coordinateList.get(i).getY(), lineRadius, paint);
                if (i == size - 2) {
                    canvas.drawCircle(coordinateList.get(i + 1).getX(), coordinateList.get(i + 1).getY(), lineRadius, paint);
                }
                paint.setXfermode(null);
            }
        }
    }

    public void setLineColor(int color) {
        this.line_color = color;
    }

    public void setLineRadius(float lineRadius) {
        this.lineRadius = lineRadius;
    }

}
