package com.common.widget;

import android.content.Context;
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

public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {

    private Paint bgPaint;
    private Paint bg2Paint;
    private Paint textPaint;
    private int headerHeight;

    private Integer[] decorationPositions = {5, 10, 20, 30, 34, 55, 78}; //有序数组
    private String[] decorationNames = {"5-title", "10-title", "20-title", "30-title", "34-title", "55-title", "78-title"};
    private final List<Integer> decorPositionList;
    private final float dp_1;
    private final Rect headerRect;
    private final Rect headerTopRect;

    public StickyHeaderDecoration(Context context) {
        dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        headerHeight = (int) (dp_1 * 25);
        this.bgPaint = new Paint();
        bgPaint.setColor(Color.YELLOW);
        bgPaint.setAntiAlias(true);

        this.bg2Paint = new Paint();
        bg2Paint.setColor(Color.YELLOW);
        bg2Paint.setAntiAlias(true);

        this.textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dp_1 * 13);
        decorPositionList = Arrays.asList(decorationPositions);

        headerRect = new Rect();
        headerTopRect = new Rect();
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (Arrays.asList(decorationPositions).contains(position)) {
            outRect.set(0, headerHeight, 0, 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (decorPositionList.contains(position)) {
                headerRect.top = child.getTop() - headerHeight;
                headerRect.bottom = child.getTop();
                headerRect.left = 0;
                headerRect.right = parent.getWidth();
                c.drawRect(headerRect, bgPaint);
                //获取相应位置的文本类容
                int indexOfText = decorPositionList.indexOf(position);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                int baseline = (int) ((headerRect.bottom + headerRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
                c.drawText(decorationNames[indexOfText], dp_1 * 20, baseline, textPaint);
            }
        }
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = parent.getChildAdapterPosition(parent.getChildAt(0));
        //当前位置以上是否存在decoration
        if (position >= decorationPositions[0]) {
            headerTopRect.left = 0;
            headerTopRect.top = 0;
            headerTopRect.right = parent.getWidth();
            headerTopRect.bottom = headerHeight;
            c.drawRect(headerTopRect, bg2Paint);
            int topDecorPosition = getTopDecorPosition(position);
            int indexOfText = decorPositionList.indexOf(topDecorPosition);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            int baseline = (int) ((headerTopRect.bottom + headerTopRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
            c.drawText(decorationNames[indexOfText], dp_1 * 20, baseline, textPaint);
        }
    }

    private int getTopDecorPosition(int currentPosition) {
        int topDecorPosition = 0;
        for (int i = 0; i < decorPositionList.size(); i++) {
            Integer value = decorPositionList.get(i);
            if (currentPosition >= value) {
                topDecorPosition = value;
            } else {
                return topDecorPosition;
            }
        }
        return topDecorPosition;
    }
}
