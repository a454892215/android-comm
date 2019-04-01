package com.test.util.fragment;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
import com.common.widget.CommonTabLayout;
import com.test.util.R;

public class TabLayoutFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_layout;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"Tab一", "Tab二", "Tab三", "Tab四", "Tab五"};
        CommonTabLayout tab_layout_1 = findViewById(R.id.tab_layout_1);
        tab_layout_1.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        tab_layout_1.setOnSelectChangedListener(position -> ToastUtil.showShort(activity, tabNames[position]));
        tab_layout_1.setCurrentPosition(0);

        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        tab_layout_2.setOnSelectChangedListener(position -> ToastUtil.showShort(activity, tabNames[position]));
        tab_layout_2.setCurrentPosition(0);


    }
}
