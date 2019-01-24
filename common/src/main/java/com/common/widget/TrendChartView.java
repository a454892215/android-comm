package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
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
    private float dp_1;
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
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
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
        linePaint.setXfermode(mode_clear);
        linePaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < joinPointList.size(); i++) {
            CoordinateEntity entity = joinPointList.get(i);
            canvas.drawCircle(entity.getX(), entity.getY(), joinRadius - strokeWidth / 2f, linePaint);
        }
    }

    public void setCoordinateList(List<CoordinateEntity> list) {
        joinPointList.clear();
        for (int i = 0; i < list.size(); i++) {
            CoordinateEntity entity = list.get(i);
            float x = entity.getX() * dp_1;
            float y = entity.getY() * dp_1;
            if (i == 0) {
                trendPath.moveTo(x, y);
            } else {
                trendPath.lineTo(x, y);
                if (i != list.size() - 1) {
                    joinPointList.add(new CoordinateEntity(x, y));
                    trendPath.addCircle(x, y, joinRadius, Path.Direction.CW);
                    trendPath.moveTo(x, y);
                }
            }
            PathMeasure pathMeasure = new PathMeasure(trendPath, false);
            float length = pathMeasure.getLength();
            LogUtil.debug("length:" + length);
        }
    }
}
