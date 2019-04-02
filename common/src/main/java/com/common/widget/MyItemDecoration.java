package com.common.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

import com.common.R;

import java.util.Arrays;
import java.util.List;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Paint bgPaint;
    private Paint bg2Paint;
    private Paint textPaint;
    private int unit;

    private Integer[] decorationPositions = {5, 10, 20, 30, 34, 55, 78}; //有序数组
    private String[] decorationNames = {"5-title", "10-title", "20-title", "30-title", "34-title", "55-title", "78-title"};
    private final List<Integer> decorPositionList;

    public MyItemDecoration() {
        this.bgPaint = new Paint();
        bgPaint.setColor(Color.YELLOW);
        bgPaint.setAntiAlias(true);

        this.bg2Paint = new Paint();
        bg2Paint.setColor(Color.YELLOW);
        bg2Paint.setAntiAlias(true);

        this.textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(30);
        decorPositionList = Arrays.asList(decorationPositions);
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (unit == 0) unit = (int) view.getResources().getDimension(R.dimen.dp_1);
        int position = parent.getChildAdapterPosition(view);
        if (Arrays.asList(decorationPositions).contains(position)) {
            outRect.set(0, unit * 20, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        int top;

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (decorPositionList.contains(position)) {
                top = child.getTop() - unit * 20;
                c.drawRect(0, top, parent.getWidth(), child.getTop(), bgPaint);
                //获取相应位置的文本类容
                int indexOfText = decorPositionList.indexOf(position);
                c.drawText(decorationNames[indexOfText], unit * 20, child.getTop() - unit * 5, textPaint);
            }
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        View child_top = parent.getChildAt(0);
        int position = parent.getChildAdapterPosition(child_top);
        //当前位置以上是否存在decoration
        if (position >= decorationPositions[0]) {
            c.drawRect(0, 0, parent.getWidth(), unit * 20, bg2Paint);
            int topNextPosition = getTopNextPosition(position);
            int indexOfText = decorPositionList.indexOf(topNextPosition);
            c.drawText(decorationNames[indexOfText], unit * 20, unit * 15, textPaint);
        }
    }

    private int getTopNextPosition(int currentPosition) {
        int topNextPosition = 0;
        for (int i = 0; i < decorPositionList.size(); i++) {
            Integer value = decorPositionList.get(i);
            if (currentPosition >= value) {
                topNextPosition = value;
            } else {
                break;
            }
        }
        return topNextPosition;
    }
}
