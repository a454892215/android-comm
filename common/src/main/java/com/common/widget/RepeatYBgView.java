package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;
import com.common.utils.ViewUtil;

/**
 * Author:  L
 * Description:垂直平铺图片背景
 */

public class RepeatYBgView extends View {


    private Drawable drawable;

    public RepeatYBgView(Context context) {
        this(context, null);
    }

    public RepeatYBgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RepeatYBgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    private void initAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RepeatYBgView, defStyleAttr, 0);
        drawable = typedArray.getDrawable(R.styleable.RepeatYBgView_pic1_src);
        typedArray.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int picHeight = Math.round(ViewUtil.getPicHeightByWidth(drawable, getWidth()));
        for (int i = 0; i < getHeight() / picHeight + 1; i++) {
            drawable.setBounds(0, i * picHeight, getWidth(), i * picHeight + picHeight);
            drawable.draw(canvas);
        }
    }


}
