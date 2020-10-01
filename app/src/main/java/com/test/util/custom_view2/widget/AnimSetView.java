package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;



/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class AnimSetView extends View {
    private Paint paint;
    private Paint textPaint;
    private Matrix matrix;


    public AnimSetView(Context context) {
        this(context, null);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public AnimSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }



}
