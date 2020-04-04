package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.widget.trend.MyPoint;
import com.common.widget.trend.TrendChartView;
import com.test.util.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrendChartFragment_2 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_chart_2;
    }

    @Override
    protected void initView() {

        TrendChartView trend_chart = findViewById(R.id.trend_chart);
        List<MyPoint> list = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            list.add(new MyPoint(i, new Random().nextInt(100) + 20));
        }
        trend_chart.setCoordinateList(list);

      /*  RefreshLayout refresh_layout = findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(refreshLayout -> {
            refresh_layout.postDelayed(refresh_layout::notifyLoadFinish,200);
        });*/
        findViewById(R.id.btn).setOnClickListener(v->{
            trend_chart.startAnimDraw();
        });
    }
}
