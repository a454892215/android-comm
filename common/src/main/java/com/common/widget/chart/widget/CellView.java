package com.common.widget.chart.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;
import com.common.comm.L;
import com.common.widget.inter.BgTextShapeFunc;
import com.common.widget.inter.LineShapeFunc;
import com.common.widget.view_func.BgTextShape;
import com.common.widget.view_func.LineShape;

import java.util.Arrays;

public class CellView extends View implements LineShapeFunc, BgTextShapeFunc {


    private Paint bgPaint;
    private RectF bgItemRectf;
    private int rectSize; //如果不设置高度 宽高都为rectSize

    private int horizontalSpacing; //水平间距

    private Paint textPaint;
    private boolean isStroke;
    private int strokeColor;

    private int rectBgRadius;
    private float strokeWidth;
    private Object[] textArr;
    private int[] bgColorArr;
    private int[] textColors;
    private int rectHeight;
    private int contentWidth;
    private int textColor;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        float dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setDither(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setTextAlign(Paint.Align.CENTER);

        strokeWidth = dp_1;

        float text_size = L.dp_1 * 13;
        strokeColor = Color.RED;
        textPaint.setTextSize(text_size);

        bgItemRectf = new RectF();
        lineShape = new LineShape(this);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectHeight = rectHeight == 0 ? rectSize : rectHeight;
        bgItemRectf.top = getPaddingTop();
        bgItemRectf.bottom = bgItemRectf.top + rectHeight;
        if (textArr != null) {
            contentWidth = rectSize * textArr.length + horizontalSpacing * (textArr.length - 1);
            if (Arrays.asList(textArr).contains("十")) {
                contentWidth = contentWidth - (horizontalSpacing - horSpacing_SZF) * 2;
            }

        }
    }

    private static final int horSpacing_SZF = Math.round(L.dp_1 * -2);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (textArr == null) return;
        float lastBgRectLeft = 0;
        for (int i = 0; i < textArr.length; i++) {
            //绘制item背景
            int hor_spacing = horizontalSpacing;
            if (Arrays.asList(textArr).contains("十")) { //特殊处理
                if (i >= 6) hor_spacing = horSpacing_SZF;
            }

            if (lastBgRectLeft == 0) {
                bgItemRectf.left = (getMeasuredWidth() - contentWidth) / 2f;
            } else {
                bgItemRectf.left = lastBgRectLeft + rectSize + hor_spacing;
            }

            bgItemRectf.top = (getMeasuredHeight() - rectHeight) / 2f;
            bgItemRectf.bottom = bgItemRectf.top + rectHeight;

            bgItemRectf.right = bgItemRectf.left + rectSize;
            if (isStroke) {
                bgPaint.setStyle(Paint.Style.STROKE);
                bgPaint.setStrokeWidth(this.strokeWidth);
                bgPaint.setColor(strokeColor);
            } else {
                bgPaint.setStyle(Paint.Style.FILL);
            }
            if (bgColorArr != null && bgColorArr.length > 0) {
                if (i < bgColorArr.length) {
                    bgPaint.setColor(bgColorArr[i]);
                } else {
                    bgPaint.setColor(0xffff0000);
                }

                canvas.drawRoundRect(bgItemRectf, rectBgRadius, rectBgRadius, bgPaint);
                lastBgRectLeft = bgItemRectf.left;
            }


            if (textColors != null) {
                textPaint.setColor(textColors[i]);
            } else {
                textPaint.setColor(textColor);
            }
            if ("十".equals(textArr[i])) { //十字符特殊处理
                textPaint.setColor(0xFFBE2A38);
            }
            float rectCenterX = bgItemRectf.left + rectSize / 2f;
            float rectCenterY = bgItemRectf.top + rectHeight / 2f;
            float baseLine = getBaseLine(textPaint, rectCenterY);
            canvas.drawText(textArr[i].toString(), rectCenterX, baseLine, textPaint);//绘制item文字
        }

        if (lineShape != null) {
            lineShape.onDraw(canvas);
        }
        if (bgTextShape != null) {
            bgTextShape.onDraw(canvas);
        }

    }

    private float getBaseLine(Paint paint, float centerY) {
        return centerY - (paint.getFontMetricsInt().ascent + paint.getFontMetricsInt().descent) / 2f;
    }

    public void setTextSize(float textSize) {
        textPaint.setTextSize(textSize);
    }

    public void setTextArr(Object[] textArr) {
        this.textArr = textArr;
        requestLayout();
    }

    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = horizontalSpacing;
    }

    public void setTextColors(int[] textColors) {
        this.textColors = textColors;
    }

    public void setStrokeBg(boolean isStroke, int strokeColor, float strokeWidth) {
        this.isStroke = isStroke;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    public void setBgColorArr(int[] bgColorArr) {
        this.bgColorArr = bgColorArr;
    }

    private LineShape lineShape;

    @Override
    public void setLineShape(LineShape bgShape) {

    }

    @Override
    public LineShape getLineShape() {
        return lineShape;
    }


    public void setRectSize(int rectSize) {
        this.rectSize = rectSize;
    }

    public void setRectHeight(int rectHeight) {
        this.rectHeight = rectHeight;
    }


    public void setRectBgRadius(int rectBgRadius) {
        this.rectBgRadius = rectBgRadius;
    }


    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    private BgTextShape bgTextShape;

    @Override
    public void setBgShape(BgTextShape bgTextShape) {
        this.bgTextShape = bgTextShape;
    }

    @Override
    public BgTextShape getBgTextShape() {
        return bgTextShape;
    }
}
