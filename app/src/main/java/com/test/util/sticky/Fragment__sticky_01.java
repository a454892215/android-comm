package com.test.util.sticky;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.test.util.R;
import com.test.util.comm.SimpleTextAdapter;
import com.test.util.comm.TestDataHelper;

public class Fragment__sticky_01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_sticky_01;
    }

    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter adapter = new SimpleTextAdapter(activity, TestDataHelper.getData(100));
        rv.setAdapter(adapter);
    }

}
