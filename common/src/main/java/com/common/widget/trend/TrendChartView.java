package com.common.widget.trend;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.common.R;
import com.common.utils.DensityUtils;
import com.common.utils.LogUtil;
import com.common.widget.trend.listener.ScaleGestureListener;
import com.common.widget.trend.listener.SimpleGestureListener;

import java.util.List;

/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class TrendChartView extends View {
    private Paint linePaint;
    private Path trendPath;
    private float xUnitLength;
    private float yUnitLength;
    private float maxCanScrollWidth;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private float dp_1;
    private Context context;
    private ValueAnimator drawAnimator;

    public TrendChartView(Context context) {
        this(context, null);
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public float getMaxCanScrollWidth() {
        return maxCanScrollWidth;
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        xUnitLength = dp_1 * 10;
        yUnitLength = dp_1;
        // float joinRadius = dp_1 * 4;
        float strokeWidth = dp_1 * 2;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setColor(Color.RED);
        //  linePaint.setStrokeJoin(Paint.Join.ROUND);
        //  linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setPathEffect(new CornerPathEffect(dp_1 * 2));
        trendPath = new Path();

        ScaleGestureListener listener = new ScaleGestureListener();
        scaleGestureDetector = new ScaleGestureDetector(context, listener);
        SimpleGestureListener simpleGestureListener = new SimpleGestureListener(this);
        gestureDetector = new GestureDetector(context, simpleGestureListener);
    }

    private boolean isClearDrawing = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isClearDrawing) {
            Drawable background = getBackground();
            if (background instanceof ColorDrawable) {
                int color = ((ColorDrawable) background).getColor();
                canvas.drawColor(color);
            } else {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//会闪烁
            }
            isClearDrawing = false;
        }
        linePaint.setXfermode(null);
        // linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(dst, linePaint);
    }

    public void setCoordinateList(List<Point> list) {
        int size = list.size();
        float startX = dp_1 * 15;
        float endPointMargin = dp_1 * 15;
        float width = DensityUtils.getWidth(context);
        maxCanScrollWidth = (size - 1) * (xUnitLength) + startX + endPointMargin - width;
        maxCanScrollWidth = maxCanScrollWidth > 0 ? maxCanScrollWidth : 0;
        LogUtil.d("===================maxCanScrollWidth:" + maxCanScrollWidth + "  xUnitLength:" + xUnitLength);
        trendPath.reset();
        // trendPath.addArc(200, 200, 200 + 200, 400, 0, 359.9f);
        //  trendPath.arcTo(400, 200, 400 + 200, 400, 180, 359.9f,false);
        //trendPath.close();

        for (int i = 0; i < size; i++) {
            Point point_1 = list.get(i);
            float x_dp_p1;
            if (i == 0) {
                x_dp_p1 = startX;
            } else {
                x_dp_p1 = startX + xUnitLength;
            }
            float y_dp_p1 = point_1.y * yUnitLength;
            startX = x_dp_p1;
            if (i == 0) {
                trendPath.moveTo(x_dp_p1, y_dp_p1);
            } else {
                trendPath.lineTo(x_dp_p1, y_dp_p1);
            }
        }
        startAnimDraw();
    }

    Path dst = new Path();

    public void startAnimDraw() {
        if (drawAnimator != null) {
            drawAnimator.pause();
            drawAnimator.cancel();
        }
        isClearDrawing = true;
        invalidate();
        PathMeasure pathMeasure = new PathMeasure(trendPath, false);
        drawAnimator = ValueAnimator.ofFloat(0, 1);
        drawAnimator.setDuration(3000);
        drawAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            float lastStart = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float length = pathMeasure.getLength();
                float end = length * value;
                dst.reset();
                boolean segment = pathMeasure.getSegment(0, end, dst, true);
                lastStart = end;
                LogUtil.d("============end:" + end + "  segment:" + segment);
                TrendChartView.this.invalidate();
            }
        });
        drawAnimator.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

/*    private Point[] getStartAndEndCoordinateOfLine(Point circleCenterP1, Point circleCenterP2, float joinRadius) {
        Point[] points_1 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP1, joinRadius);
        Point[] points_2 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP2, joinRadius);
        return new Point[]{points_1[1], points_2[0]};
    }*/
}
