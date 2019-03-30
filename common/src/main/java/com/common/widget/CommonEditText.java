package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import com.common.R;


/**
 * Author:  L
 * Description: No
 */

public class CommonEditText extends android.support.v7.widget.AppCompatEditText {

    private int bg_border_color;
    private float bg_border_thick;
    private int bg_color;
    private float bg_radius;
    private Paint paint;
    private boolean bg_enable;
    private PorterDuffXfermode mode_clear;
    private boolean isClearBg;

    public CommonEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public CommonEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CommonEditText, defStyleAttr, 0);
        String hint_text = typedArray.getString(R.styleable.CommonEditText_hint_text);
        int hint_size = Math.round(typedArray.getDimension(R.styleable.CommonEditText_hint_size, 33));
        int hint_color = typedArray.getColor(R.styleable.CommonEditText_hint_color, Color.GRAY);

        bg_border_color = typedArray.getColor(R.styleable.CommonEditText_bg_border_color, Color.RED);
        bg_border_thick = typedArray.getDimension(R.styleable.CommonEditText_bg_border_thick, 5f);
        bg_color = typedArray.getColor(R.styleable.CommonEditText_bg_color, Color.BLACK);
        bg_radius = typedArray.getDimension(R.styleable.CommonEditText_bg_radius, 10);
        bg_enable = typedArray.getBoolean(R.styleable.CommonEditText_bg_enable, false);
        isClearBg = typedArray.getBoolean(R.styleable.CommonEditText_bg_clear_bg_enable, false);
        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(bg_border_thick);
        mode_clear = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        setHintText(hint_text, hint_color, hint_size);
    }

    private void setHintText(String hintText, int hint_color, int hint_size) {
        hintText = hintText == null ? "" : hintText;
        SpannableString ss = new SpannableString(hintText);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hint_size, false);
        setHintTextColor(hint_color);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setHint(new SpannedString(ss));
    }

    public void setClearBg(boolean clearBg) {
        isClearBg = clearBg;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (bg_enable) {
            paint.setXfermode(null);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(bg_color);
            drawBg(canvas);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(bg_border_color);
            drawBg(canvas);
        }
        if (isClearBg) {
            paint.setXfermode(mode_clear);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        }
        super.onDraw(canvas);
    }

    private void drawBg(Canvas canvas) {
        int paddingBottom = getPaddingBottom();
        int width = getWidth();
        int height = getHeight();
        canvas.drawRoundRect(bg_border_thick / 2, bg_border_thick / 2, width - bg_border_thick / 2,
                height - paddingBottom - bg_border_thick / 2, bg_radius, bg_radius, paint);
    }
}
