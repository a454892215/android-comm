package com.common.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.common.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint bgPaint;
    private Paint textPaint;
    private int unit;

    public MyItemDecoration() {
        this.bgPaint = new Paint();
        bgPaint.setColor(Color.YELLOW);
        bgPaint.setAntiAlias(true);

        this.textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (unit == 0) unit = (int) view.getResources().getDimension(R.dimen.dp_1);
        int position = parent.getChildAdapterPosition(view);
        if (position % 5 == 0 && position != 0) {
            outRect.set(0, unit * 20, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int top;
        int right;
        int bottom;
        String text;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            top = child.getTop() - unit * 20;
            right = child.getRight();
            bottom = child.getTop();
            if (index % 5 == 0 && index != 0) {
                c.drawRect(0, top, right, bottom, bgPaint);
                text = "我是位置：" + index + "  top:" + top;
                c.drawText(text, unit * 20, bottom - unit * 5, textPaint);
            }
          /*  if (top <= 0) {
                c.drawRect(left, top, right, bottom, bgPaint);
                c.drawText("我是位置：" + index + "  top:" + top, left + unit * 20, bottom - unit * 5, textPaint);
            }*/
        }

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
