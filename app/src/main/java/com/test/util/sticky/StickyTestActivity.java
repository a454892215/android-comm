package com.test.util.sticky;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;


public class StickyTestActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager view_pager = findViewById(R.id.view_pager);
        List<Fragment> list = new ArrayList<>();
        list.add(new Fragment__sticky_01());
        list.add(new Fragment__sticky_02());
        list.add(new Fragment__sticky_03());
        view_pager.setAdapter(new MyFragmentAdapter(fm, list));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_sticky_test;
    }
}
