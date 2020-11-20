package com.test.util.sticky;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.common.utils.LogUtil;
import com.common.widget.CommonTabLayout;
import com.test.util.R;
import com.test.util.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;


public class StickyTestActivity2 extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPager view_pager = findViewById(R.id.view_pager);
        List<Fragment> list = new ArrayList<>();
        list.add(new Fragment__sticky_01());
        list.add(new Fragment__sticky_02());
        list.add(new Fragment__sticky_01());
        list.add(new Fragment__sticky_02());
        list.add(new Fragment__sticky_01());
        list.add(new Fragment__sticky_02());
        list.add(new Fragment__sticky_01());
        view_pager.setAdapter(new MyFragmentAdapter(fm, list));
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                float progress = position + positionOffset;
                LogUtil.d("===========position:" + position + "  progress:" + progress);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // CommonTabLayout tab_layout = findViewById(R.id.tab_layout);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_sticky_test2;
    }
}
