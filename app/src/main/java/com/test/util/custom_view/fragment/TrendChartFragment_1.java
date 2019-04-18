package com.test.util.custom_view.fragment;

import android.content.Context;

import com.common.base.BaseFragment;
import com.common.utils.CastUtil;
import com.common.utils.InstanceUtil;
import com.common.widget.CommonTabLayout;
import com.common.widget.chart.DataHandler_temple;
import com.common.widget.chart.DataHandler_temple_2;
import com.common.widget.chart.IDataHandler;
import com.common.widget.chart.RowCell;
import com.common.widget.chart.widget.CustomTableView;
import com.common.widget.refresh.RefreshLayout;
import com.test.util.R;

import java.util.List;

public class TrendChartFragment_1 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_chart_1;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"组合走势图1", "组合走势图2", "组合走势图3", "组合走势图4"};
        Class dataHandlerArr[] = {DataHandler_temple.class, DataHandler_temple.class, DataHandler_temple.class, DataHandler_temple_2.class,};
        CustomTableView table_view = findViewById(R.id.table_view);
        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        tab_layout_2.setOnSelectChangedListener(position -> {
            IDataHandler dataHandler = InstanceUtil.getInstance(CastUtil.cast(dataHandlerArr[position]), new Class[]{Context.class}, new Object[]{activity});
            if (dataHandler != null) {
                List<List<RowCell>> lists = dataHandler.handleData();
                table_view.setHeaderData(lists.get(0), lists.get(1));
                table_view.setBodyData(lists.get(2), lists.get(3));
            }
        });
        tab_layout_2.setCurrentPosition(0);
        RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        //refresh_layout.setNotInterceptEvent(true);//此处手势，可以在refresh中更细节的处理
        refresh_layout.setTargetView(table_view.table_rv_body_left, table_view.table_rv_body_right);
        refresh_layout.setOnRefreshListener(refreshLayout -> refreshLayout.postDelayed(refreshLayout::notifyLoadFinish, 500));
        refresh_layout.setOnLoadMoreListener(refreshLayout -> refreshLayout.postDelayed(refreshLayout::notifyLoadFinish, 500));

    }
}
