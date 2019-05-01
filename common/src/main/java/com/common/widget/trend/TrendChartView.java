package com.common.widget.trend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.common.R;
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
    private float joinRadius;
    private float xUnitLength;
    private float yUnitLength;
    private float maxVisibleWidth;
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;
    private float dp_1;

    public TrendChartView(Context context) {
        this(context, null);
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public float getMaxVisibleWidth() {
        return maxVisibleWidth;
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        xUnitLength = dp_1 * 50;
        yUnitLength = dp_1;
        joinRadius = dp_1 * 4;
        float strokeWidth = dp_1 * 2;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setPathEffect(new CornerPathEffect(dp_1 * 20));
        trendPath = new Path();

        ScaleGestureListener listener = new ScaleGestureListener();
        scaleGestureDetector = new ScaleGestureDetector(context, listener);
        SimpleGestureListener simpleGestureListener = new SimpleGestureListener(this);
        gestureDetector = new GestureDetector(context, simpleGestureListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePaint.setXfermode(null);
        linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(trendPath, linePaint);
    }

    public void setCoordinateList(List<Point> list) {
        int size = list.size();
        float startX = dp_1 * 15;
        maxVisibleWidth = (size - 1) * (xUnitLength - joinRadius * 2) + startX;
        LogUtil.d("===================maxVisibleWidth:" + maxVisibleWidth + "  xUnitLength:" + xUnitLength);
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
            trendPath.moveTo(x_dp_p1, y_dp_p1);
            trendPath.addCircle(x_dp_p1, y_dp_p1, joinRadius, Path.Direction.CW);
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * Calculate the start and end coordinates of the line
     */

/*    private Point[] getStartAndEndCoordinateOfLine(Point circleCenterP1, Point circleCenterP2, float joinRadius) {
        Point[] points_1 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP1, joinRadius);
        Point[] points_2 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP2, joinRadius);
        return new Point[]{points_1[1], points_2[0]};
    }*/
}
