package com.test.util.sticky;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.common.utils.LogUtil;
import com.common.widget.CommonTabLayout;
import com.google.android.material.appbar.AppBarLayout;
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
        view_pager.setAdapter(new MyFragmentAdapter(fm, list));
        CommonTabLayout tab_layout = findViewById(R.id.tab_layout);
        tab_layout.setIndicatorViewId(R.id.flt_indicator);
        tab_layout.bindViewPagerAndIndicator(view_pager);
        tab_layout.setCurrentPosition(0);


        AppBarLayout app_bar_layout = findViewById(R.id.app_bar_layout);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            private int lastVerticalOffset = 0;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(lastVerticalOffset != verticalOffset){
                    float totalScrollRange = appBarLayout.getTotalScrollRange();
                    float percent = Math.abs(verticalOffset / totalScrollRange);
                    lastVerticalOffset = verticalOffset;
                    LogUtil.d("verticalOffset:" + verticalOffset + "   percent:" + percent + " totalScrollRange:" + totalScrollRange);
                }

            }
        });
        // CommonTabLayout tab_layout = findViewById(R.id.tab_layout);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.aty_sticky_test2;
    }
}
