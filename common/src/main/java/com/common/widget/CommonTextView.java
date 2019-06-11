package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import androidx.appcompat.widget.AppCompatTextView;

import com.common.R;

/**
 * Author:  L
 * CreateDate: 2018/12/20
 * Description: No
 */

public class CommonTextView extends AppCompatTextView {
    private float topLineStrokeWidth;
    private float bottomLineStrokeWidth;
    private float leftLineStrokeWidth;
    private float rightLineStrokeWidth;


    private int topLineColor;

    private int bottomLineColor;
    private int leftLineColor;
    private int rightLineColor;

    private Paint linePaint;
    private boolean leftLineEnable;
    private boolean rightLineEnable;
    private boolean topLineEnable;
    private boolean bottomLineEnable;


    private LinearGradient mLinearGradient;
    private float clipRadius;

    public CommonTextView(Context context) {
        this(context, null);
    }

    public CommonTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);
        if (clipRadius > 0) {
            this.setClipToOutline(true);
            this.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
                    outline.setRoundRect(rect, clipRadius);
                }
            });
        }
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonTextView, defStyleAttr, 0);
        topLineColor = typedArray.getColor(R.styleable.CommonTextView_top_line_color, Color.BLACK);
        bottomLineColor = typedArray.getColor(R.styleable.CommonTextView_bottom_line_color, Color.BLACK);
        rightLineColor = typedArray.getColor(R.styleable.CommonTextView_right_line_color, Color.BLACK);
        leftLineColor = typedArray.getColor(R.styleable.CommonTextView_left_line_color, Color.BLACK);

        topLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_top_line_height, 0);
        bottomLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_bottom_line_height, 0);
        rightLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_right_line_stroke_width, 0);
        leftLineStrokeWidth = typedArray.getDimension(R.styleable.CommonTextView_left_line_stroke_width, 0);

        topLineEnable = topLineStrokeWidth != 0;
        bottomLineEnable = bottomLineStrokeWidth != 0;
        rightLineEnable = rightLineStrokeWidth != 0;
        leftLineEnable = leftLineStrokeWidth != 0;

        clipRadius = typedArray.getDimension(R.styleable.CommonTextView_clip_radius, 0);
        typedArray.recycle();
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

        if (mLinearGradient != null) {
            Paint mPaint = getPaint();
            mPaint.setShader(mLinearGradient);
        }

        super.onDraw(canvas);
    }

    @SuppressWarnings("unused")
    public void setClipRadius(float radius) {
        this.clipRadius = radius;
    }

    @SuppressWarnings("unused")
    public void setLinearGradient(LinearGradient linearGradient) {
        this.mLinearGradient = linearGradient;
    }

    public void setLeftLineColor(int leftLineColor) {
        this.leftLineColor = leftLineColor;
    }

    public void setTopLineColor(int topLineColor) {
        this.topLineColor = topLineColor;
    }

    public void setRightLineColor(int rightLineColor) {
        this.rightLineColor = rightLineColor;
    }

    public void setBottomLineColor(int bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

    @SuppressWarnings("unused")
    public void setTopLineStrokeWidth(float topLineStrokeWidth) {
        this.topLineStrokeWidth = topLineStrokeWidth;
        topLineEnable = true;
    }

    public void setBottomLineStrokeWidth(float bottomLineStrokeWidth) {
        this.bottomLineStrokeWidth = bottomLineStrokeWidth;
        bottomLineEnable = true;
    }

    @SuppressWarnings("unused")
    public void setLeftLineStrokeWidth(float leftLineStrokeWidth) {
        this.leftLineStrokeWidth = leftLineStrokeWidth;
        leftLineEnable = true;
    }

    public void setRightLineStrokeWidth(float rightLineStrokeWidth) {
        this.rightLineStrokeWidth = rightLineStrokeWidth;
        rightLineEnable = true;
    }
}
