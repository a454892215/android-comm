package com.test.util.custom_view.fragment;

import android.graphics.Point;

import com.common.base.BaseFragment;
import com.common.widget.trend.TrendChartView;
import com.test.util.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrendChartFragment_1 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trend_chart_1;
    }

    @Override
    protected void initView() {
        TrendChartView trend_chart = findViewById(R.id.trend_chart);
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int unit_x = 20;
            list.add(new Point(unit_x * i + unit_x, new Random().nextInt(100) + 20));
        }
        trend_chart.setCoordinateList(list);
    }
}
