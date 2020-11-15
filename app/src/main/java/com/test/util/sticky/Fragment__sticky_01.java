package com.test.util.sticky;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseFragment;
import com.common.widget.banner.BannerLayout;
import com.common.widget.banner.BannerUtil;
import com.test.util.R;
import com.test.util.comm.SimpleTextAdapter;
import com.test.util.comm.TestDataHelper;

import java.util.Arrays;

public class Fragment__sticky_01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.frag_sticky_01;
    }

    private static String[] imgUrl = {"http://img17.3lian.com/d/file/201702/21/8f8a5c670f68613382cb043d1ad2fe05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/1fa7ef2fbf14cb7640ea50de1914cd05.jpg"
            , "http://img17.3lian.com/d/file/201702/21/44b2c79be750dcc69f919bc786cbd173.jpg"
            , "http://img17.3lian.com/d/file/201702/21/834c9af2d7b02b74a1d9d44b527c53ff.jpg"
            , "http://img17.3lian.com/d/file/201702/21/8c49c4da75a889cc3c4ceb211a2adaa3.jpg"};
    @Override
    protected void initView() {
        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        SimpleTextAdapter adapter = new SimpleTextAdapter(activity, TestDataHelper.getData(100));
        rv.setAdapter(adapter);

        BannerUtil.initBanner(findViewById(R.id.banner), Arrays.asList(imgUrl));
    }

}
