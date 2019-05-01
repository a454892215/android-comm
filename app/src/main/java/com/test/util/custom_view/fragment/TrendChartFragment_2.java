package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.widget.trend.Point;
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
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Point(i, new Random().nextInt(100) + 20));
        }
        trend_chart.setCoordinateList(list);
    }
}
