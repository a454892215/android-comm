package com.test.util.candle;

import android.graphics.Color;
import android.graphics.Paint;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
import com.common.widget.CommonTabLayout;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.test.util.R;

import java.util.ArrayList;
import java.util.List;

public class CoinFragment01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coin01_layout;
    }

    @Override
    protected void initView() {
        initData();
    }

    private void initData(){
        CandleStickChart chart = findViewById(R.id.candleStickChart);
        // 生成 5000 条 K 线数据
        List<CandleEntry> entries = generateKLineData(5000);

        // 设置数据集
        CandleDataSet dataSet = new CandleDataSet(entries, "K线图");
        dataSet.setColor(Color.rgb(80, 80, 80));
        dataSet.setShadowColor(Color.DKGRAY);
        dataSet.setShadowWidth(0.7f);
        dataSet.setDecreasingColor(Color.RED); // 跌
        dataSet.setDecreasingPaintStyle(Paint.Style.FILL);
        dataSet.setIncreasingColor(Color.GREEN); // 涨
        dataSet.setIncreasingPaintStyle(Paint.Style.FILL);
        dataSet.setNeutralColor(Color.BLUE);
        dataSet.setBarSpace(0.2f); // 调整为适当的值，增加 K 线之间的间距

        // 数据绑定
        CandleData data = new CandleData(dataSet);
        chart.setData(data);

        // 配置图表
        configureChart(chart);

        // 刷新图表
        chart.invalidate();
    }

    private void configureChart(CandleStickChart chart) {
        chart.setDragEnabled(true); // 拖动
        chart.setScaleEnabled(true); // 缩放
        chart.setPinchZoom(true); // 捏合缩放

        // 配置 X 轴
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // 防止跳跃
        xAxis.setLabelCount(5, false);

        // 配置 Y 轴
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false); // 隐藏右轴

        // 其他属性
        chart.getDescription().setEnabled(false);
        chart.setVisibleXRangeMaximum(100); // 最大可见 100 条数据
        chart.setVisibleXRangeMinimum(20);  // 最小可见 20 条数据
    }

    private List<CandleEntry> generateKLineData(int count) {
        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float high = (float) (Math.random() * 100 + 200); // 高点
            float low = (float) (Math.random() * 100 + 100);  // 低点
            float open = (float) (Math.random() * (high - low) + low); // 开盘价
            float close = (float) (Math.random() * (high - low) + low); // 收盘价
            entries.add(new CandleEntry(i, high, low, open, close));
        }
        return entries;
    }


}
