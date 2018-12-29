package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.common.R;
import com.common.adapter.common.HLayoutManager;
import com.common.utils.LogUtil;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class ForegroundRecyclerView extends HRecyclerView {

    private int red;
    private TextPaint paint;

    public ForegroundRecyclerView(Context context) {
        this(context, null);
    }

    public ForegroundRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        red = Color.parseColor("#ff0000");
        paint = new TextPaint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(context.getResources().getDimension(R.dimen.sp_10));
    }

    public ForegroundRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);
        canvas.drawColor(red);
        for (int i = 0; i < 50; i++) {
            canvas.drawLine( 20,100*i + 10,400,100*i + 10,paint);
            canvas.drawText(""+i,20,100*i + 10,paint);
        }
    }
}