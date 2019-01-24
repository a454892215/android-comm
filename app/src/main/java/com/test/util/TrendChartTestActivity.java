package com.test.util;

import android.os.Bundle;
import android.view.View;

import com.common.base.BaseActivity;
import com.common.widget.TrendChartView;
import com.common.widget.table.CoordinateEntity;

import java.util.ArrayList;
import java.util.List;

public class TrendChartTestActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TrendChartView trend_chart = findViewById(R.id.trend_chart);
        List<CoordinateEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            int unit_x = 20;
            list.add(new CoordinateEntity(unit_x * i + unit_x, i % 2 == 0 ? 100 : 50));
        }
        trend_chart.setCoordinateList(list);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.trend_chart_test;
    }
}
