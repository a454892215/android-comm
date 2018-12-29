package com.test.util;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.common.adapter.TextViewAdapter;
import com.common.adapter.common.RecyclerViewUtil;
import com.common.base.BaseActivity;
import com.common.base.BaseRecyclerViewAdapter;
import com.common.utils.FastClickUtil;

import java.util.Arrays;

/**
 * Author: Pan
 * Description:
 */
public class MainActivity extends BaseActivity {

    private String[] names = {"RecyclerView 同步滚动和自定义水平滚动测试", "ListView 同步滚动测试",
            "GridRV测试", "自定义LayoutManager测试", "可水平滚动的垂直RecycleView", "前景绘制测试"};
    private Class[] classArr = {RVAsyncScrollTestActivity.class, ListViewAsyncTestActivity.class,
            RVGridTestActivity.class, CustomLayoutManagerTestActivity.class, HRVTestActivity.class,
            ForegroundDrawingTestActivity.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("API测试");
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        BaseRecyclerViewAdapter adapter = new TextViewAdapter(activity, R.layout.view_btn_1, Arrays.asList(names));
        RecyclerViewUtil.setRecyclerView(recycler_view, adapter);
        adapter.setOnItemClick((itemView, position) -> {
            if (FastClickUtil.isFastClick()) return;
            Intent intent = new Intent(activity, classArr[position]);
            intent.putExtra("title", names[position]);
            startActivity(intent);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
