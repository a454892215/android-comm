package com.test.util.custom_view2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.common.comm.L;


/**
 * Author:  L
 * CreateDate: 2010/10/01 17:05
 * Description: 贝塞尔曲线
 */

public class BezierCurveView extends View {
    private Paint paint;
    private Path path;


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
        paint.setColor(Color.GREEN);
        path = new Path();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(L.dp_1 * 20, L.dp_1 * 100); // 起点
        float controlX = L.dp_1 * 60;
        float controlY = L.dp_1 * 100;
        path.quadTo(controlX, controlY, L.dp_1 * 220, L.dp_1 * 100); // 控制点和终点
        canvas.drawPath(path, paint);

        canvas.drawCircle(controlX, controlY, L.dp_1 * 3, paint); // 绘制控制点

    }


}
