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
import com.common.utils.ViewUtil;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class TestCusView2 extends View {
    private Paint paint;
    private Paint textPaint;


    public TestCusView2(Context context) {
        this(context, null);
    }

    public TestCusView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public TestCusView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();
        textPaint = new TextPaint();
        textPaint.setTextSize(L.dp_1 * 10);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = L.dp_1 * 5;
        float top = L.dp_1 * 5;
        float right = L.dp_1 * 55;
        float bottom = L.dp_1 * 55;
        paint.setColor(Color.parseColor("#58985A"));
        canvas.drawRect(left, top, right, bottom, paint);
        drawGrid(canvas);
        canvas.save();

        // 平移示例
        canvas.translate(L.dp_1 * 70, 0); // 平移, 不会影响之前的操作
        paint.setColor(Color.parseColor("#58985A"));
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.drawText("平移示例", left, ViewUtil.getBaseLine(textPaint, bottom + L.dp_1 * 12), textPaint);

        //缩放示例
        // canvas.translate(-L.dp_1 * 70, 0); // 复位方式1
        canvas.restore();// 复位方式2
        canvas.save();
        canvas.scale(0.5f, 0.5f, left, top); // 缩放, 不会影响之前的操作
        paint.setColor(Color.parseColor("#cccccc"));
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.drawText("缩放示例", left, ViewUtil.getBaseLine(textPaint, bottom + L.dp_1 * 12), textPaint);

        //旋转示例
        // canvas.scale(2, 2, left, top); // 复位方式1
        canvas.restore();// 复位方式2
        canvas.save();
        canvas.translate(L.dp_1 * 150, 0); //先平移, 不会影响之前的操作
        canvas.rotate(45, left + right / 2f, top + bottom / 2f); //旋转, 不会影响之前的操作
        paint.setColor(Color.parseColor("#cccccc"));
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.drawText("旋转示例", left, ViewUtil.getBaseLine(textPaint, bottom + L.dp_1 * 12), textPaint);

        //斜切示例
        canvas.restore();// 复位
        canvas.save();
        canvas.translate(L.dp_1 * 230, 0); //先平移, 不会影响之前的操作
        canvas.skew(1, 0); // X方向倾斜45度， 宽度大，高不变
        paint.setColor(Color.parseColor("#cccccc"));
        canvas.drawRect(left, top, right, bottom, paint);
        canvas.drawText("斜切示例", left, ViewUtil.getBaseLine(textPaint, bottom + L.dp_1 * 12), textPaint);
    }


    private void drawGrid(Canvas canvas) {
        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
        for (int i = 1; i < 60; i++) {
            canvas.drawLine(0, i * 10 * L.dp_1, getWidth(), i * 10 * L.dp_1, paint);
        }
    }


}
