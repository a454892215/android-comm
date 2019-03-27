package com.common.adapter.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.utils.LogUtil;

/**
 * Author:  L
 * Description: No
 */

public class HVLayoutManager extends RecyclerView.LayoutManager {
    private int column;
    private int canScrollHeight = 0;
    private int verticalContentHeight;

    public HVLayoutManager(int column) {
        this.column = column;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    private int lastVisibleItemPosition = 0;
    private int firstVisibleItemPosition = 0;

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //如果没有item，直接返回
        if (getItemCount() <= 0) return;
        // 跳过preLayout，preLayout主要用于支持动画
        if (state.isPreLayout()) {
            return;
        }
        verticalContentHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        detachAndScrapAttachedViews(recycler);//在布局之前，将所有的子View先Detach掉，放入到Scrap缓存中
        int offsetY = 0;
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View itemView = recycler.getViewForPosition(i);
            addView(itemView);
            measureChildWithMargins(itemView, 0, 0);
            int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            layoutDecorated(itemView, 0, offsetY, width, offsetY + height);
            lastVisibleItemPosition = i;
            offsetY += height;
            //   LogUtil.d("onLayoutChildren====i :" + i + "  offsetY:" + offsetY);
            if (offsetY > verticalContentHeight) break; //满屏
        }
        computeSizeInfo(recycler, verticalContentHeight);
    }

    private void computeSizeInfo(RecyclerView.Recycler recycler, int verticalContentHeight) {
        int itemTotalHeight = getPaddingTop();
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View itemView = recycler.getViewForPosition(i);
            measureChildWithMargins(itemView, 0, 0);
            // int width = getDecoratedMeasuredWidth(itemView);
            int height = getDecoratedMeasuredHeight(itemView);
            itemTotalHeight += height;
            // LogUtil.d("onLayoutChildren====i :" + i + "  itemTotalHeight:" + itemTotalHeight);
        }
        canScrollHeight = itemTotalHeight - verticalContentHeight;
        LogUtil.d("onLayoutChildren======canScrollHeight:" + canScrollHeight + " itemTotalHeight:" + itemTotalHeight);
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

        } else if (dy_scrolled + dy > canScrollHeight) { //底边界判断
            dy = canScrollHeight - dy_scrolled;
        }
        offsetChildrenVertical(-dy);
        dy_scrolled += dy;
        int childCount = getChildCount();
        View firstVisibleView = getChildAt(0);
        View lastVisibleView = getChildAt(childCount - 1);
        int firstVisibleViewTop = firstVisibleView.getTop();
        int lastVisibleViewBottom = lastVisibleView.getBottom();

        if (dy > 0 && firstVisibleView.getBottom() < 0) {//向上滑 第一个可见条目 离屏回收
            removeAndRecycleView(firstVisibleView, recycler);
            View view = recycler.getViewForPosition(lastVisibleItemPosition + 1);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int height = getDecoratedMeasuredHeight(view);
            int width = getDecoratedMeasuredWidth(view);
            layoutDecorated(view, getPaddingLeft(), lastVisibleViewBottom, width + getPaddingLeft(), lastVisibleViewBottom + height);
            firstVisibleItemPosition++;
            lastVisibleItemPosition++;
            LogUtil.d("===============: childCount "+ childCount +"  dy: "+dy +"  lastVisibleItemPosition:"+lastVisibleItemPosition);
        }

        int lastViewTop = lastVisibleView.getTop();
        if (dy < 0 && lastViewTop > verticalContentHeight) {//向上滑 最后一个可见条目 离屏回收
            removeAndRecycleView(lastVisibleView, recycler);
            View view = recycler.getViewForPosition(firstVisibleItemPosition - 1);
            addView(view,0);
            measureChildWithMargins(view, 0, 0);
            int height = getDecoratedMeasuredHeight(view);
            int width = getDecoratedMeasuredWidth(view);
            layoutDecorated(view, getPaddingLeft(), firstVisibleViewTop - height, width + getPaddingLeft(), firstVisibleViewTop);
            firstVisibleItemPosition--;
            lastVisibleItemPosition--;
        }
        return dy;
    }

}
