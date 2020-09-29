package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.util.AttributeSet;

import android.view.View;

import androidx.annotation.Nullable;


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


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

}
