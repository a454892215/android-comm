package com.common.widget.rv;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.common.R;

import java.util.List;

public class StickyHeaderDecoration extends RecyclerView.ItemDecoration {

    private float textLeftMargin;
    private Paint headerPaint;
    private Paint topHeaderPaint;
    private Paint textPaint;
    private int headerHeight;

    private List<Integer> decorPositionList;

    private List<String> decorNameList;
    private final Rect headerRect;
    private final Rect headerTopRect;

    public StickyHeaderDecoration(Context context) {
        float dp_1 = context.getResources().getDimension(R.dimen.dp_1);
        headerHeight = (int) (dp_1 * 25);
        textLeftMargin = dp_1 * 20;

        this.headerPaint = new Paint();
        headerPaint.setColor(Color.YELLOW);
        headerPaint.setAntiAlias(true);

        this.topHeaderPaint = new Paint();
        topHeaderPaint.setColor(Color.YELLOW);
        topHeaderPaint.setAntiAlias(true);

        this.textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dp_1 * 13);

        headerRect = new Rect();
        headerTopRect = new Rect();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (decorPositionList.contains(position)) {
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
                c.drawRect(headerRect, headerPaint);
                //?????????????????????????????????
                int indexOfText = decorPositionList.indexOf(position);
                Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
                int baseline = (int) ((headerRect.bottom + headerRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
                c.drawText(decorNameList.get(indexOfText), textLeftMargin, baseline, textPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = parent.getChildAdapterPosition(parent.getChildAt(0));
        //??????????????????????????????decoration
        if (position >= decorPositionList.get(0)) {
            headerTopRect.left = 0;
            headerTopRect.top = 0;
            headerTopRect.right = parent.getWidth();
            //????????????
            View child_1 = parent.getChildAt(1);
            int child_2_position = parent.getChildAdapterPosition(child_1);
            if (decorPositionList.contains(child_2_position) && child_1.getTop() < headerHeight * 2) {
                headerTopRect.top = child_1.getTop() - headerHeight * 2;
            }
            headerTopRect.bottom = headerTopRect.top + headerHeight;
            c.drawRect(headerTopRect, topHeaderPaint);
            int topDecorPosition = getTopDecorPosition(position);
            int indexOfText = decorPositionList.indexOf(topDecorPosition);
            Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
            int baseline = (int) ((headerTopRect.bottom + headerTopRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
            c.drawText(decorNameList.get(indexOfText), textLeftMargin, baseline, textPaint);
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

    public StickyHeaderDecoration setTextSize(float textSize) {
        textPaint.setTextSize(textSize);
        return this;
    }

    public StickyHeaderDecoration setTextColor(int textColor) {
        textPaint.setColor(textColor);
        return this;
    }

    @SuppressWarnings("unused")
    public StickyHeaderDecoration setTextLeftMargin(float leftMargin) {
        this.textLeftMargin = leftMargin;
        return this;
    }

    @SuppressWarnings("unused")
    public StickyHeaderDecoration setHeaderBgColor(int bgColor) {
        headerPaint.setColor(bgColor);
        topHeaderPaint.setColor(bgColor);
        return this;
    }

    @SuppressWarnings("unused")
    public StickyHeaderDecoration setDecorPositionList(List<Integer> decorPositionList) {
        this.decorPositionList = decorPositionList;
        return this;
    }

    @SuppressWarnings("unused")
    public StickyHeaderDecoration setDecorNameList(List<String> decorNameList) {
        this.decorNameList = decorNameList;
        return this;
    }

    public StickyHeaderDecoration setHeaderHeight(int headerHeight) {
        this.headerHeight = headerHeight;
        return this;
    }
}
