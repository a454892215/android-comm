package com.common.adapter.common;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.utils.LogUtil;

/**
 * Author:  Pan
 * CreateDate: 2018/12/27 13:36
 * Description: No
 */

public class HVLayoutManager extends RecyclerView.LayoutManager {
    private int column;
    private int verticalCanScrollHeight;

    public HVLayoutManager(int column) {
        this.column = column;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        LogUtil.d("onLayoutChildren=================");
        detachAndScrapAttachedViews(recycler);//在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            View itemView = recycler.getViewForPosition(i);  //这里就是从缓存里面取出
            addView(itemView);

            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            layoutDecorated(itemView, 0, offsetY, width, offsetY + height);
            offsetY += height;
            LogUtil.d("onLayoutChildren====i :" + i +"  offsetY:"+offsetY);
        }
        int totalHeight = offsetY;
        int verticalContentHeight = getVerticalContentHeight();
        verticalCanScrollHeight = totalHeight - verticalContentHeight;
    }

    private int getVerticalContentHeight() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    private int dy_scrolled;

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (dy_scrolled + dy < 0) { //顶边界判断
            dy = 0 - dy_scrolled;

        }else if(dy_scrolled + dy > verticalCanScrollHeight){ //底边界判断
            dy = verticalCanScrollHeight - dy_scrolled;
        }
        offsetChildrenVertical(-dy);
        dy_scrolled += dy;
        LogUtil.d("===============dy_scrolled:" + dy_scrolled + " verticalCanScrollHeight:" + verticalCanScrollHeight);
        return dy;
    }

    private Rect getScrolledContentRect() {
        return  new Rect(getPaddingLeft(), getPaddingTop() + dy_scrolled,
                getWidth() - getPaddingRight(), getVerticalContentHeight() + dy_scrolled);
    }

}
