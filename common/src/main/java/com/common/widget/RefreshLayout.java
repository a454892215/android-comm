package com.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.common.utils.LogUtil;

/**
 * Author: Pan
 * 2018/12/26
 * Description:
 */
public class RefreshLayout extends LinearLayout {

    private View headerView;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headerView = getChildAt(0);
        LogUtil.d(" =====onFinishInflate headerView:" + headerView.getMeasuredHeight());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtil.d("===== onMeasure==== headerView:" + headerView.getMeasuredHeight());
    }

    public interface onRefreshListener {
        void onRefresh(RefreshLayout refreshLayout);
    }

    public interface onLoadMoreListener {
        void onLoadMore(RefreshLayout refreshLayout);
    }

    public View getHeaderView() {
        return headerView;
    }

}