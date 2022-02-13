package com.common.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import com.common.comm.L;
import com.common.widget.HScrollContentView;
import com.common.widget.entity.ViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2022/2/13
 * Description:
 */
@SuppressWarnings("unused")
public class UIDrawTest {

    private final List<ViewItem> drawList = new ArrayList<>();
    private TextPaint paint = new TextPaint();
    private final List<ViewItem> testTotalData = new ArrayList<>();

    private void init() {
        paint = new TextPaint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(L.dp_1 * 8);

        for (int i = 0; i < 2000; i++) {
            ViewItem viewItem = new ViewItem();
            viewItem.data = i + "";
            viewItem.colorIndex = i;
            testTotalData.add(viewItem);
        }
    }

    private void draw(Canvas canvas, HScrollContentView view) {
        computeDrawingList(ViewItem.itemWidth, testTotalData, view);
        int drawListSize = drawList.size();
        for (int i = 0; i < drawListSize; i++) {
            ViewItem viewItem = drawList.get(i);
            paint.setColor(viewItem.colorIndex % 2 == 0 ? Color.RED : Color.GREEN);
            canvas.drawRect(viewItem.start, viewItem.top, viewItem.end, viewItem.bottom, paint);
            canvas.drawText(viewItem.data.toString(), viewItem.start + ViewItem.itemWidth / 2f, viewItem.top, paint);
        }
    }

    private void computeDrawingList(float itemWidth, List<ViewItem> totalList, HScrollContentView view) {
        if (totalList == null || totalList.size() == 0) return;
        int totalSize = totalList.size();
        view.setMaxScrollWidth(itemWidth * totalSize - view.getMeasuredWidth());
        float scrolledX = view.mScroller.getFinalX(); //已经滚过的距离
        //每次最多只绘制N屏
        int sizeOfOneDraw = (int) (view.getMeasuredWidth() * 5 / itemWidth + 1);
        drawList.clear();
        float scrolledItemSize = scrolledX / itemWidth; //已经滚过的Item数目
        int start = (int) Math.floor(scrolledItemSize);
        for (int i = start; i < start + sizeOfOneDraw; i++) {
            if (i < totalSize) {
                ViewItem viewItem = totalList.get(i);
                viewItem.offset = -(scrolledItemSize - start) * itemWidth;
                drawList.add(viewItem);
            }
        }
        int size = drawList.size();
        for (int i = 0; i < size; i++) {
            ViewItem viewItem = drawList.get(i);
            viewItem.start = viewItem.offset + i * itemWidth;
            viewItem.end = viewItem.start + itemWidth;
            viewItem.top = L.dp_1 * 20;
            viewItem.bottom = viewItem.top + ViewItem.itemHeight;
        }
    }
}
