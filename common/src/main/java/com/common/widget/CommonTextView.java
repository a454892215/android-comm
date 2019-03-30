package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.common.R;

/**
 * Author:  L
 * CreateDate: 2018/12/20
 * Description: No
 */

public class CommonTextView extends android.support.v7.widget.AppCompatTextView {
    private float topLineStrokeWidth;
    private float bottomLineStrokeWidth;
    private float leftLineStrokeWidth;
    private float rightLineStrokeWidth;

    private int topLineColor;
    private int bottomLineColor;
    private int leftLineColor;
    private int rightLineColor;

    private Paint linePaint;
    private final boolean leftLineEnable;
    private boolean rightLineEnable;
    private boolean topLineEnable;
    private boolean bottomLineEnable;


    public CommonTextView(Context context) {
        this(context, null);
    }

    public CommonTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTextView, defStyleAttr, 0);

        topLineEnable = typedArray.getBoolean(R.styleable.CommonTextView_top_line_enable, false);
        bottomLineEnable = typedArray.getBoolean(R.styleable.CommonTextView_bottom_line_enable, false);
        rightLineEnable = typedArray.getBoolean(R.styleable.CommonTextView_right_line_enable, false);
        leftLineEnable = typedArray.getBoolean(R.styleable.CommonTextView_left_line_enable, false);


        topLineColor = typedArray.getColor(R.styleable.CommonTextView_top_line_color, Color.BLACK);
        bottomLineColor = typedArray.getColor(R.styleable.CommonTextView_bottom_line_color, Color.BLACK);
        rightLineColor = typedArray.getColor(R.styleable.CommonTextView_right_line_color, Color.BLACK);
        leftLineColor = typedArray.getColor(R.styleable.CommonTextView_left_line_color, Color.BLACK);

        float dp_1 = getResources().getDimension(R.dimen.dp_1);
        topLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_top_line_height, dp_1);
        bottomLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_bottom_line_height, dp_1);
        rightLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_right_line_stroke_width, dp_1);
        leftLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_left_line_stroke_width, dp_1);
        typedArray.recycle();

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
//        LogUtil.debug("leftLineEnable:" + leftLineEnable);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (rightLineEnable) {
            linePaint.setStrokeWidth(rightLineStrokeWidth);
            linePaint.setColor(rightLineColor);
            canvas.drawLine(getWidth() - rightLineStrokeWidth / 2, 0, getWidth() - rightLineStrokeWidth / 2, getHeight(), linePaint);
        }

        if (leftLineEnable) {
            linePaint.setStrokeWidth(leftLineStrokeWidth);
            linePaint.setColor(leftLineColor);
            canvas.drawLine(leftLineStrokeWidth / 2, 0, leftLineStrokeWidth / 2, getHeight(), linePaint);
        }

        if (topLineEnable) {
            linePaint.setStrokeWidth(topLineStrokeWidth);
            linePaint.setColor(topLineColor);
            canvas.drawLine(0, topLineStrokeWidth / 2, getWidth(), topLineStrokeWidth / 2, linePaint);
        }

        if (bottomLineEnable) {
            linePaint.setStrokeWidth(bottomLineStrokeWidth);
            linePaint.setColor(bottomLineColor);
            canvas.drawLine(0, getHeight() - bottomLineStrokeWidth / 2, getWidth(), getHeight() - bottomLineStrokeWidth / 2, linePaint);
        }
        super.onDraw(canvas);
    }

}
