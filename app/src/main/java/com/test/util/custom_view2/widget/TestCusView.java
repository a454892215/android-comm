package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Paint;
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


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startX = 0;
        float startY = L.dp_1 * 20;
        float endX = L.dp_1 * 280;
        paint.setStrokeWidth(L.dp_1 * 10);
        canvas.drawLine(startX, startY, endX, startY, paint);

        // top = startY - StrokeWidth/2 = 20 -
        canvas.drawLine(L.dp_1 * 300, startY, L.dp_1 * 300, startY + L.dp_1 * 10, paint);
        paint.setStrokeWidth(1);
        paint.setColor(Color.WHITE);
        for (int i = 1; i < 200; i++) {
            canvas.drawLine(0, i * 10 * L.dp_1, getWidth(), i * 10 * L.dp_1, paint);
        }


    }

}
