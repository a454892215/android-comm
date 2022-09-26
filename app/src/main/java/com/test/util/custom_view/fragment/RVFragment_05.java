package com.test.util.custom_view.fragment;

import com.common.base.BaseFragment;
import com.common.helper.FragmentHelper;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.custom_view.rv.RVTest1Fragment;
import com.test.util.custom_view.rv.RVTest2Fragment;
import com.test.util.custom_view.rv.RVTest3Fragment;
import com.test.util.custom_view.rv.F4_Banner;
import com.test.util.custom_view.rv.RVTest5Fragment;

public class RVFragment_05 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rv;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"粘性头部", "底入动画", "滑动删除", "轮播图", "水平RV"};
        Class[] classArr = {RVTest1Fragment.class, RVTest2Fragment.class, RVTest3Fragment.class, F4_Banner.class, RVTest5Fragment.class};
        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        FragmentHelper fragmentHelper = new FragmentHelper(fm, classArr, R.id.flt_content);
        tab_layout_2.setOnSelectChangedListener(fragmentHelper::onSwitchFragment);
        tab_layout_2.setCurrentPosition(0);
    }
}
