package com.test.util.sticky;

import android.widget.ListView;

import com.common.base.BaseFragment;
import com.test.util.R;
import com.test.util.comm.SimpleLvTextAdapter;
import com.test.util.comm.TestDataHelper;

public class Fragment__sticky_02 extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.frag_sticky_02;
    }

    @Override
    protected void initView() {
        ListView list_view = findViewById(R.id.list_view);
        list_view.setAdapter(new SimpleLvTextAdapter(TestDataHelper.getData(100)));
    }

}
