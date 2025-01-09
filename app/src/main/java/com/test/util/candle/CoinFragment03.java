package com.test.util.candle;

import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.common.base.BaseFragment;
import com.common.utils.ToastUtil;
import com.common.widget.CommonTabLayout;
import com.test.util.R;

public class CoinFragment03 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coin02_layout;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"Tab一", "Tab二", "Tab三", "Tab四", "Tab五"};

        CommonTabLayout tab_layout_1 = findViewById(R.id.tab_layout_1);
        tab_layout_1.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout_1.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        tab_layout_1.setOnSelectChangedListener(position -> ToastUtil.showLong(tabNames[position]));
        tab_layout_1.setCurrentPosition(0);

    }


}
