package com.test.util.custom_view2.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.common.comm.L;
import com.test.util.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * Author:  L
 * CreateDate: 2019/1/23 17:05
 * Description: 翻页效果View
 */

public class BookView extends View {
    private Paint paint;

    public BookView(Context context) {
        this(context, null);
    }

    public BookView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }


    public BookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null); // 图层混合模式使用 必须关闭硬件加速
        paint = new Paint();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


}
