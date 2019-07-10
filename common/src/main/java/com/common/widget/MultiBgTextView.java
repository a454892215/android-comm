package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;

public class MultiBgTextView extends View {


    private Paint bgPaint;
    private RectF bgItemRectf;
    private int rectSize;

    private int horizontalSpacing; //水平间距

    private int itemCount;
    private String[] textArr;
    private int horizontalUnitSpacing; //水平单位间距
    private Paint textPaint;
    private boolean isStroke = false;
    private int strokeColor;
    private int rectBgRadius = 0;
    private float strokeWidth;
    private int[] bgColorArr;


    private int[] textColors;

    public MultiBgTextView(Context context) {
        this(context, null);
    }

    public MultiBgTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MultiBgTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.sp_17));

        rectSize = Math.round(context.getResources().getDimension(R.dimen.dp_24));
        horizontalSpacing = Math.round(context.getResources().getDimension(R.dimen.dp_5));
        strokeWidth = context.getResources().getDimension(R.dimen.dp_1);
        bgItemRectf = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        bgItemRectf.top = getPaddingTop();
        bgItemRectf.bottom = bgItemRectf.top + rectSize;
        horizontalUnitSpacing = rectSize + horizontalSpacing;
        int width = getPaddingStart() + getPaddingEnd() + rectSize * itemCount + horizontalSpacing * (itemCount - 1);
        if (width < 0) width = 0;
        setMeasuredDimension(width, getPaddingTop() + getPaddingBottom() + rectSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < itemCount; i++) {
            //绘制item背景
            bgItemRectf.left = getPaddingStart() + horizontalUnitSpacing * i;
            bgItemRectf.right = bgItemRectf.left + rectSize;
            if (isStroke) {
                bgPaint.setStyle(Paint.Style.STROKE);
                bgPaint.setStrokeWidth(this.strokeWidth);
                bgPaint.setColor(strokeColor);
            } else {
                bgPaint.setStyle(Paint.Style.FILL);
                bgPaint.setColor(bgColorArr[i]);
            }
            canvas.drawRoundRect(bgItemRectf, rectBgRadius, rectBgRadius, bgPaint);

            if (textColors != null) {
                textPaint.setColor(textColors[i]);
            }

            float rectCenterX = bgItemRectf.left + rectSize / 2f;
            float rectCenterY = bgItemRectf.top + rectSize / 2f;
            float baseLine = getBaseLine(textPaint, rectCenterY);
            canvas.drawText(textArr[i], rectCenterX, baseLine, textPaint);//绘制item文字
        }

    }

    private float getBaseLine(Paint paint, float centerY) {
        return centerY - (paint.getFontMetricsInt().ascent + paint.getFontMetricsInt().descent) / 2f;
    }


    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
    }

    public void setTextSize(float textSize) {
        textPaint.setTextSize(textSize);
    }

    public void setTextArr(String[] textArr, int[] bgColorArr) {
        itemCount = textArr.length;
        this.textArr = textArr;
        this.bgColorArr = bgColorArr;
        requestLayout();
    }


    public void setTextColor(int[] textColors) {
        this.textColors = textColors;
    }

    public void setStrokeBg(int strokeColor, int rectBgRadius, float strokeWidth) {
        isStroke = true;
        this.rectBgRadius = rectBgRadius;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }


    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

}
