package com.test.util.custom_view2.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.common.utils.ViewUtil;
import com.test.util.R;

import org.jetbrains.annotations.NotNull;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class AnimSetView extends View {
    private Paint mPaint = new Paint();
    private Paint mHolePaint = new Paint();
    private int[] color_arr = {Color.RED, Color.YELLOW, Color.BLACK, Color.BLUE, Color.GREEN, Color.CYAN};
    // 旋转圆中心坐标
    private float centerX;
    private float centerY;

    // 最大扩散圆半径
    private float cur_circle_radius = L.dp_1 * 20; //旋转圆半径
    private float min_circle_radius = L.dp_1 * 3;
    private float max_circle_radius = L.dp_1 * 28;
    // 6个小圆半径
    private float ball_radius = L.dp_1 * 3;
    private float curAngle = 0;
    private BitmapDrawable drawable;

    private float bg_circle_radius = 0;
    private PorterDuffXfermode mode;


    public AnimSetView(Context context) {
        this(context, null);
    }

    public AnimSetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    private static final float rotate_v = 4f; // 圆环旋转速度

    public AnimSetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        mHolePaint.setColor(Color.WHITE);
        ValueAnimator animator_1 = prepareAnim1();
        setOnClickListener(v -> {
            cur_circle_radius = L.dp_1 * 20; //旋转圆半径
            animator_1.start();
        });

        setOnLongClickListener(v -> {
            animator_1.pause();
            animator_1.cancel();
            invalidate();
            return true;
        });
        Bitmap bitmap_fj = BitmapFactory.decodeResource(context.getResources(), R.mipmap.feng_jing);
        drawable = new BitmapDrawable(context.getResources(), bitmap_fj);
        mode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }

    @NotNull
    private ValueAnimator prepareAnim1() {
        ValueAnimator animator_1 = ValueAnimator.ofFloat(0, 1);
        animator_1.setDuration(1000);
        animator_1.setInterpolator(new LinearInterpolator());
        animator_1.addUpdateListener(animation -> {
            float change_angle = (float) (Math.PI / 180f * rotate_v);
            curAngle += change_angle;
            invalidate();
        });
        animator_1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startAnim2();
            }
        });
        return animator_1;
    }

    private void startAnim2() {
        ValueAnimator animator_2 = ValueAnimator.ofFloat(cur_circle_radius, max_circle_radius, min_circle_radius);
        animator_2.setDuration(800);
        animator_2.setInterpolator(new AccelerateInterpolator());
        animator_2.addUpdateListener(anim -> {
            cur_circle_radius = (float) anim.getAnimatedValue();
            invalidate();
        });
        animator_2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startAnim3();
            }
        });
        animator_2.start();
    }

    private void startAnim3() {
        ValueAnimator animator_3 = ValueAnimator.ofFloat(max_circle_radius, getHeight() / 2f);
        animator_3.setDuration(500);
        animator_3.setInterpolator(new AccelerateInterpolator());
        animator_3.addUpdateListener(anim -> {
            bg_circle_radius = (float) anim.getAnimatedValue();
            invalidate();
        });
        animator_3.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                bg_circle_radius = 0;
                invalidate();
            }
        });
        animator_3.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getMeasuredWidth() / 2f;
        centerY = getMeasuredHeight() / 2f;
        drawable.setBounds(ViewUtil.getScaledRect(drawable, getMeasuredWidth(), getMeasuredHeight()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawable.draw(canvas);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);//保留上层 标记1
        canvas.drawColor(Color.GRAY);
        float per_rad = (float) (Math.PI * 2 / color_arr.length);
        // 绘制6个小球
        for (int i = 0; i < color_arr.length; i++) {
            float angle = per_rad * i + curAngle;
            float cx = (float) (centerX + cur_circle_radius * Math.cos(angle));
            float cy = (float) (centerY + cur_circle_radius * Math.sin(angle));
            mPaint.setColor(color_arr[i]);
            canvas.drawCircle(cx, cy, ball_radius, mPaint);
        }
        //mHolePaint.setStrokeWidth(L.dp_1*720 - bg_circle_radius);

        mHolePaint.setXfermode(mode); //分界线 ，清空标记1 到setXfermode 之间的内容
        canvas.drawCircle(centerX, centerY, bg_circle_radius, mHolePaint);
        canvas.restoreToCount(layerId);
    }


}
