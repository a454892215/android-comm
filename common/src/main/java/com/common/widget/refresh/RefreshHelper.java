package com.common.widget.refresh;

import androidx.recyclerview.widget.RecyclerView;

import com.common.helper.AdapterHelper;

import java.util.List;

public class RefreshHelper {
    private int currentPageIndex;
    private int startPageIndex;
    private int per_page_count;
    private RefreshLayout refreshLayout;
    private RecyclerView rv;

    public RefreshHelper(RefreshLayout refreshLayout, RecyclerView rv, int startPageIndex, int per_page_count) {
        this.startPageIndex = startPageIndex;
        this.currentPageIndex = startPageIndex;
        this.refreshLayout = refreshLayout;
        this.rv = rv;
        this.per_page_count = per_page_count;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void init(OnRefreshListener onRefreshListener, OnLoadMoreListener onLoadMoreListener) {
        refreshLayout.setOnRefreshListener(onRefreshListener);
        refreshLayout.setOnLoadMoreListener(onLoadMoreListener);
    }

    public void onRequestFinished(List list, int requestPageIndex) {
        refreshLayout.notifyLoadFinish();
        if (requestPageIndex == startPageIndex) {
            AdapterHelper.notifyAdapterRefresh(list, rv);
        } else {
            AdapterHelper.notifyAdapterLoadMore(list, rv);
        }
        if (list.size() < per_page_count) {
            refreshLayout.setFooterFunction(FooterFunction.only_display);
            //  refresh_layout.setFooterTextOnOnlyDisplay(itemCount + "条记录加载完毕");
        } else {
            refreshLayout.setFooterFunction(FooterFunction.load_more);
        }
        currentPageIndex = requestPageIndex;
    }

}
