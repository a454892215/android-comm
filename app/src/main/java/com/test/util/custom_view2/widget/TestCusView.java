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


    public TestCusView(Context context) {
        this(context, null);
    }

    public TestCusView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public TestCusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();
        textPaint = new TextPaint();
        textPaint.setTextSize(L.dp_1 * 10);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float bottom = testStrokeCap(canvas);
        testXModes(canvas, bottom);
        drawGrid(canvas);
    }

    private void testXModes(Canvas canvas, float bottom) {
        float left_1 = L.dp_1 * 20;
        float left_2 = left_1 + L.dp_1 * 90;
        float left_3 = left_2 + L.dp_1 * 90;
        float left_4 = left_3 + L.dp_1 * 90;
        testXMode(canvas, left_1, bottom, PorterDuff.Mode.SRC, "SRC");
        testXMode(canvas, left_2, bottom, PorterDuff.Mode.CLEAR, "CLEAR");
        testXMode(canvas, left_3, bottom, PorterDuff.Mode.DST, "DST");
        testXMode(canvas, left_4, bottom, PorterDuff.Mode.SRC_OVER, "SRC_OVER");

        bottom = bottom + L.dp_1 * 70;
        testXMode(canvas, left_1, bottom, PorterDuff.Mode.DST_OVER, "DST_OVER");
        testXMode(canvas, left_2, bottom, PorterDuff.Mode.SRC_IN, "SRC_IN");
        testXMode(canvas, left_3, bottom, PorterDuff.Mode.DST_IN, "DST_IN");
        testXMode(canvas, left_4, bottom, PorterDuff.Mode.SRC_OUT, "SRC_OUT");

        bottom = bottom + L.dp_1 * 70;
        testXMode(canvas, left_1, bottom, PorterDuff.Mode.DST_OUT, "DST_OUT");
        testXMode(canvas, left_2, bottom, PorterDuff.Mode.SRC_ATOP, "SRC_ATOP");
        testXMode(canvas, left_3, bottom, PorterDuff.Mode.DST_ATOP, "DST_ATOP");
        testXMode(canvas, left_4, bottom, PorterDuff.Mode.XOR, "XOR");

        bottom = bottom + L.dp_1 * 70;
        testXMode(canvas, left_1, bottom, PorterDuff.Mode.DARKEN, "DARKEN");
        testXMode(canvas, left_2, bottom, PorterDuff.Mode.LIGHTEN, "LIGHTEN");
        testXMode(canvas, left_3, bottom, PorterDuff.Mode.MULTIPLY, "MULTIPLY");
        testXMode(canvas, left_4, bottom, PorterDuff.Mode.SCREEN, "SCREEN");

        bottom = bottom + L.dp_1 * 80;
        canvas.drawText("16种图层模式示例", getWidth() / 2f, ViewUtil.getBaseLine(textPaint, bottom), textPaint);
    }

    private void testXMode(Canvas canvas, float left, float bottom, PorterDuff.Mode mode, String name) {
        float radius = L.dp_1 * 20;
        float cx = radius + left;
        float cy = bottom + radius + L.dp_1 * 10;
        //DST_OUT
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        paint.setXfermode(null);
        paint.setStrokeWidth(1);
        paint.setColor(Color.parseColor("#9958985A"));
        canvas.drawCircle(cx, cy, radius, paint);
        paint.setColor(Color.parseColor("#99cccccc"));
        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawCircle(cx + radius, cy, radius, paint);
        paint.setXfermode(null);
        bottom = cy + radius + L.dp_1 * 10;
        canvas.drawText(name, cx, ViewUtil.getBaseLine(textPaint, bottom), textPaint);
        canvas.restoreToCount(layerId);
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
