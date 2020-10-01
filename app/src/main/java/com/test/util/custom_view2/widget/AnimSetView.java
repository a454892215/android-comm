package com.test.util.custom_view2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.common.utils.LogUtil;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class AnimSetView extends View {
    private Paint mPaint = new Paint();
    private Paint mHolePaint = new Paint();
    private ValueAnimator valueAnimator;
    private int mbgColor = Color.WHITE;
    private int[] color_arr = {Color.RED, Color.YELLOW, Color.BLACK, Color.BLUE, Color.GREEN, Color.CYAN};
    // 旋转圆中心坐标
    private float centerX;
    private float centerY;

    // 最大扩散圆半径
    private float max_radius = L.dp_1 * 30;
    private float circle_radius = L.dp_1 * 20; //旋转圆半径
    // 6个小圆半径
    private float ball_radius = L.dp_1 * 3;


    public AnimSetView(Context context) {
        this(context, null);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public AnimSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        mHolePaint.setColor(mbgColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2f;
        centerY = getMeasuredHeight() / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float per_rad = (float) (Math.PI * 2 / color_arr.length);
        // 绘制6个小球
        for (int i = 0; i < color_arr.length; i++) {
            float angle = per_rad * i;
            float cx = (float) (centerX + circle_radius * Math.cos(angle));
            float cy = (float) (centerY + circle_radius * Math.sin(angle));
            LogUtil.d("cx:" + cx + " cy:" + cy + "  index:" + i + "  angle:" + angle);
            mPaint.setColor(color_arr[i]);
            canvas.drawCircle(cx, cy, ball_radius, mPaint);
        }
    }


}
