package com.common.helper;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Author:  Pan
 * Description: No
 */

public class RefreshHelper {
    public static void setSmartRefreshLayout(SmartRefreshLayout refreshLayout, CallbackAdapter callbackAdapter) {
        ClassicsHeader classicsHeader = new ClassicsHeader(refreshLayout.getContext());
        //classicsHeader.setAccentColor(textColor);
        refreshLayout.setRefreshHeader(classicsHeader);
        refreshLayout.setOnRefreshListener(callbackAdapter::onRefresh);

        ClassicsFooter classicsFooter = new ClassicsFooter(refreshLayout.getContext());
       // classicsFooter.setAccentColor(textColor);
        refreshLayout.setRefreshFooter(classicsFooter);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnLoadMoreListener(callbackAdapter::onLoadMore);
    }

    public static class CallbackAdapter {
        public void onRefresh(RefreshLayout refreshLayout) {
        }

        public void onLoadMore(RefreshLayout refreshLayout) {
        }
    }
}
