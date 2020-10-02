package com.test.util.custom_view2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:  L
 * CreateDate: 2010/10/01 17:05
 * Description: 贝塞尔曲线
 */

public class BezierCurveView extends View {
    private Paint paint;
    private Path path;
    private float controlX = L.dp_1 * 90;
    private static final float initControlY = L.dp_1 * 150;
    private float controlY = initControlY;

    public BezierCurveView(Context context) {
        this(context, null);
    }

    public BezierCurveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public BezierCurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();
        path = new Path();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        controlX = event.getX();
        controlY = event.getY();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            playAnim();
        }
        invalidate();
        return true;
    }

    private List<Float> zhen_dang_pos_list = new ArrayList<>();

    private void playAnim() {
        zhen_dang_pos_list.clear();
        zhen_dang_pos_list = MathUtil.getZhenD(controlY, L.dp_1 * 5);


        LogUtil.d(" zhen_dang_pos_list:" + zhen_dang_pos_list.size() + "  :" + zhen_dang_pos_list);
        ValueAnimator animator_1 = ValueAnimator.ofFloat(0, 1);
        animator_1.setDuration(1000);
        animator_1.setInterpolator(new LinearInterpolator());
        animator_1.addUpdateListener(animation -> {
            invalidate();
        });

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset(); // 必须清空
        float startX = L.dp_1 * 80;
        float StartY = L.dp_1 * 200;
        float endX = L.dp_1 * 280;
        float endY = L.dp_1 * 200;

        path.moveTo(startX, StartY); // 起点
        path.quadTo(controlX, controlY, endX, endY); // 控制点和终点
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(L.dp_1 * 3);
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(startX, StartY, L.dp_1 * 3, paint); // 绘制起点
        paint.setColor(Color.BLUE);
        canvas.drawCircle(controlX, controlY, L.dp_1 * 3, paint); // 绘制控制点
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(endX, endY, L.dp_1 * 3, paint); // 绘制控终点

        // 绘制回弹轨迹点
        for (int i = 0; i < zhen_dang_pos_list.size(); i++) {
            paint.setColor(Color.GRAY);
            canvas.drawCircle(controlX, zhen_dang_pos_list.get(i), L.dp_1 * 3, paint); // 绘制控终点
        }
    }

}
