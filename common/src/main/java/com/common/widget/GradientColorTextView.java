package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * author: ${VenRen}
 * created on: 2019/2/15 10:43
 * description: 渐变色文字
 */
public class GradientColorTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    public GradientColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setLinearGradient(LinearGradient linearGradient) {
        this.mLinearGradient = linearGradient;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint mPaint = getPaint();
        mPaint.setShader(mLinearGradient);
        super.onDraw(canvas);
    }
}
