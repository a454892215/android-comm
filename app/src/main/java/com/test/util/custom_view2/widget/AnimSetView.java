package com.test.util.custom_view2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class AnimSetView extends View {
    private Paint mPaint = new Paint();
    private Paint mHolePaint = new Paint();
    private int mbgColor = Color.WHITE;
    private int[] color_arr = {Color.RED, Color.YELLOW, Color.BLACK, Color.BLUE, Color.GREEN, Color.CYAN};
    // 旋转圆中心坐标
    private float centerX;
    private float centerY;

    // 最大扩散圆半径
    private float circle_radius = L.dp_1 * 20; //旋转圆半径
    private float min_circle_radius = L.dp_1 * 5; //旋转圆半径
    private float max_circle_radius = L.dp_1 * 30;
    // 6个小圆半径
    private float ball_radius = L.dp_1 * 3;
    private float curAngle = 0;


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

        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setRepeatCount(-1);
        animator.setDuration(2000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> {
            updateUI();
            invalidate();
        });
        setOnClickListener(v -> animator.start());

        setOnLongClickListener(v -> {
            animator.pause();
            animator.cancel();
            invalidate();
            return true;
        });

    }

    int rotate_v = 1; // 旋转半径变化速度

    private void updateUI() {
        curAngle += Math.PI / 180f * 2;
        if (circle_radius == min_circle_radius) rotate_v *= -1;
        if (circle_radius == max_circle_radius) rotate_v *= -1;
        circle_radius += rotate_v;
        circle_radius = MathUtil.clamp(circle_radius, min_circle_radius, max_circle_radius);
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
            float angle = per_rad * i + curAngle;
            float cx = (float) (centerX + circle_radius * Math.cos(angle));
            float cy = (float) (centerY + circle_radius * Math.sin(angle));
            mPaint.setColor(color_arr[i]);
            canvas.drawCircle(cx, cy, ball_radius, mPaint);
        }
    }


}
