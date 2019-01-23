package com.test.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.common.adapter.TextViewRVAdapter;
import com.common.adapter.common.RecyclerViewUtil;
import com.common.base.BaseActivity;
import com.common.base.BaseRVAdapter;
import com.common.comm.MyCountDownTimer;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;

import java.util.Arrays;

/**
 * Author: Pan
 * Description:
 */
public class MainActivity extends BaseActivity {

    private String[] names = {"基于Scroller的水平滚动测试",
            "GridRV测试", "自定义LayoutManager测试", "自定义可水平滚动的垂直RecycleView", "前景绘制测试",
            "自定义下拉刷新和加载更多", "ConstraintLayout测试", "Xposed框架测试", "进程保活验证", "自定义曲线走势图表"};
    private Class[] classArr = {ScrollerHSVTestActivity.class,
            RVGridTestActivity.class, CustomLayoutManagerTestActivity.class, HRVTestActivity.class,
            ForegroundDrawingTestActivity.class, RefreshRvTestActivity.class, ConstraintLayoutTestActivity.class,
            XposedTestActivity.class, ProgressLiveTestActivity.class, TrendChartTestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("API测试");
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        BaseRVAdapter adapter = new TextViewRVAdapter(activity, R.layout.view_btn_1, Arrays.asList(names));
        RecyclerViewUtil.setRecyclerView(recycler_view, adapter);
        adapter.setOnItemClick((itemView, position) -> {
            if (FastClickUtil.isFastClick()) return;
            Intent intent = new Intent(activity, classArr[position]);
            intent.putExtra("title", names[position]);
            startActivity(intent);
        });
        MyCountDownTimer timer = new MyCountDownTimer(30, 2000);
        timer.setOnTickListener((time, count) -> LogUtil.debug("我还活着 onTick time:" + time + " count:" + count));
        timer.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
