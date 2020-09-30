package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.TextPaint;
import android.util.AttributeSet;

import android.view.View;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.common.utils.ViewUtil;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class TestCusView extends View {
    private Paint paint;
    private Paint textPaint;
    private Context context;
    private PorterDuffXfermode xfermode;


    public TestCusView(Context context) {
        this(context, null);
    }

    public TestCusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public TestCusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();
        textPaint = new TextPaint();
        textPaint.setTextSize(L.dp_1 * 12);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bottom = testStrokeCap(canvas);
        testXMode(canvas, bottom);
        drawGrid(canvas);
    }

    private void testXMode(Canvas canvas, float bottom) {
        paint.setXfermode(null);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#58985A"));
        float radius = L.dp_1 * 30;
        float x = radius + L.dp_1 * 10;
        float y = bottom + radius + L.dp_1 * 10;
        canvas.drawCircle(x, y, radius, paint);
        paint.setColor(Color.YELLOW);
        paint.setXfermode(xfermode);
        canvas.drawCircle(x + radius, y, radius, paint);
        paint.setXfermode(null);
    }

    private void drawGrid(Canvas canvas) {
        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
        for (int i = 1; i < 60; i++) {
            canvas.drawLine(0, i * 10 * L.dp_1, getWidth(), i * 10 * L.dp_1, paint);
        }
    }

    private float testStrokeCap(Canvas canvas) {
        float startX = L.dp_1 * 5;
        float startY = L.dp_1 * 10;
        float endX = L.dp_1 * 280;
        paint.setColor(Color.parseColor("#58985A"));
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

        float bottomTextCenter = L.dp_1 * 80;
        canvas.drawText("StrokeCap 三种类型示例", getWidth() / 2f, ViewUtil.getBaseLine(textPaint, bottomTextCenter), textPaint);
        return bottomTextCenter + L.dp_1 * 7;
    }

}
