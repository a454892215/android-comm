package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import android.view.View;

import androidx.annotation.Nullable;

import com.common.comm.L;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class TestCusView extends View {
    private Paint paint;
    private Paint textPaint;
    private Context context;


    public TestCusView(Context context) {
        this(context, null);
    }

    public TestCusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public TestCusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.RED);

        textPaint = new TextPaint();
        textPaint.setTextSize(L.dp_1 * 12);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        testStrokeCap(canvas);

        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
        for (int i = 1; i < 20; i++) {
            canvas.drawLine(0, i * 10 * L.dp_1, getWidth(), i * 10 * L.dp_1, paint);
        }


    }

    private void testStrokeCap(Canvas canvas) {
        float startX = L.dp_1 * 5;
        float startY = L.dp_1 * 10;
        float endX = L.dp_1 * 280;
        paint.setStrokeWidth(L.dp_1 * 10);
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(startX, L.dp_1 * 20, endX, L.dp_1 * 20, paint);

        paint.setStrokeCap(Paint.Cap.ROUND); // 加入StrokeWidth为半径的原型头尾
        canvas.drawLine(startX, L.dp_1 * 40, endX, L.dp_1 * 40, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);// 加入StrokeWidth为长度的矩形头尾
        canvas.drawLine(startX, L.dp_1 * 60, endX, L.dp_1 * 60, paint);

        // top = startY - StrokeWidth/2 = 20 -
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(L.dp_1 * 300, startY, L.dp_1 * 300, startY + L.dp_1 * 50, paint);

        paint.setStrokeCap(Paint.Cap.ROUND); // 加入StrokeWidth为半径的原型头尾
        canvas.drawLine(L.dp_1 * 320, startY, L.dp_1 * 320, startY + L.dp_1 * 50, paint);

        paint.setStrokeCap(Paint.Cap.SQUARE);// 加入StrokeWidth为长度的矩形头尾
        canvas.drawLine(L.dp_1 * 340, startY, L.dp_1 * 340, startY + L.dp_1 * 50, paint);

        canvas.drawText("StrokeCap 三种类型示例", getWidth() / 2f, L.dp_1 * 80, textPaint);
    }

}
