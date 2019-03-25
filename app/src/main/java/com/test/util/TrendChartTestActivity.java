package com.test.util;

import android.graphics.Point;
import android.os.Bundle;

import com.common.base.BaseActivity;
import com.common.widget.trend.TrendChartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrendChartTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrendChartView trend_chart = findViewById(R.id.trend_chart);
        List<Point> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int unit_x = 20;
            list.add(new Point(unit_x * i + unit_x, new Random().nextInt(100)+20));
        }
        trend_chart.setCoordinateList(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trend_chart_test;
    }
}
