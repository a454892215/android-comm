package com.test.util;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.adapter.TextViewAdapter;
import com.common.base.BaseActivity;
import com.common.base.BaseRecyclerViewAdapter;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;

import java.util.Arrays;


public class MainActivity extends BaseActivity {

    private String[] names = {"RecyclerView 测试"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_view.setLayoutManager(linearLayoutManager);
        TextViewAdapter textViewAdapter = new TextViewAdapter(activity, R.layout.view_btn_1, Arrays.asList(names));
        textViewAdapter.setOnItemClick((itemView, position) -> ToastUtil.showShort(activity, ":" + position));
        recycler_view.setAdapter(textViewAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}
