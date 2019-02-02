package com.common.widget.trend;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;
import com.common.utils.LogUtil;
import com.common.widget.table.CoordinateEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  Pan
 * CreateDate: 2019/1/23 17:05
 * Description: No
 */

public class TrendChartView extends View {
    private final PorterDuffXfermode mode_clear;
    private final float strokeWidth;
    private Paint linePaint;
    private Path trendPath;
    private int dp_1;
    private List<CoordinateEntity> joinPointList = new ArrayList<>();
    private float joinRadius;

    public TrendChartView(Context context) {
        this(context, null);
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TrendChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp_1 = Math.round(context.getResources().getDimension(R.dimen.dp_1));
        joinRadius = dp_1 * 4;
        strokeWidth = dp_1 * 2;

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mode_clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);

        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setColor(Color.RED);
        linePaint.setStrokeJoin(Paint.Join.ROUND);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setPathEffect(new CornerPathEffect(dp_1 * 20));
        trendPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        linePaint.setXfermode(null);
        linePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(trendPath, linePaint);
    }

    public void setCoordinateList(List<Point> list) {
        joinPointList.clear();
        Point startPoint = new Point();
        Point endPoint = new Point();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Point point_1 = list.get(i);
            int x_dp_p1 = point_1.x * dp_1;
            int y_dp_p1 = point_1.y * dp_1;
            trendPath.moveTo(x_dp_p1, y_dp_p1);
            trendPath.addCircle(x_dp_p1, y_dp_p1, joinRadius, Path.Direction.CW);
            if (i != size - 1) {
                startPoint.set(x_dp_p1, y_dp_p1);
                endPoint.set(list.get(i + 1).x * dp_1, list.get(i + 1).y * dp_1);
                Point[] linePoint = getStartAndEndCoordinateOfLine(startPoint, endPoint, joinRadius);
                trendPath.moveTo(linePoint[0].x, linePoint[0].y);
                trendPath.lineTo(linePoint[1].x, linePoint[1].y);
            }
        }
    }

    /**
     * Calculate the start and end coordinates of the line
     */
    private Point[] getStartAndEndCoordinateOfLine(Point circleCenterP1, Point circleCenterP2, float joinRadius) {
        Point[] points_1 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP1, joinRadius);
        Point[] points_2 = CoordinateComputeHelper.getIntersection(circleCenterP1, circleCenterP2, circleCenterP2, joinRadius);
        return new Point[]{points_1[1], points_2[0]};
    }
}
