package com.test.util.custom_view.fragment;

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

public class TabFragment_01 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab_layout;
    }

    @Override
    protected void initView() {
        String[] tabNames = {"Tab一", "Tab二", "Tab三", "Tab四", "Tab五"};

        CommonTabLayout tab_layout_2 = findViewById(R.id.tab_layout_2);
        tab_layout_2.setData(tabNames, R.layout.template_hor_scroll_tab_item_2, R.id.tv);
        tab_layout_2.setOnSelectChangedListener(position -> ToastUtil.showShort(tabNames[position]));
        tab_layout_2.setCurrentPosition(0);

        CommonTabLayout tab_layout_1 = findViewById(R.id.tab_layout_1);
        tab_layout_1.setIndicatorViewId(R.id.flt_tab_indicator);
        tab_layout_1.setData(tabNames, R.layout.template_hor_scroll_tab_item_1, R.id.tv);
        tab_layout_1.setOnSelectChangedListener(position -> ToastUtil.showLong(tabNames[position]));
        tab_layout_1.setCurrentPosition(0);

        interpolatorTest();
    }

    private void interpolatorTest() {
        ViewGroup llt_move_content = findViewById(R.id.llt_move_content);
        Class[] interpolatorArr = {DecelerateInterpolator.class, AccelerateInterpolator.class,
                AccelerateDecelerateInterpolator.class, AnticipateInterpolator.class, AnticipateOvershootInterpolator.class,
                BounceInterpolator.class, CycleInterpolator.class, LinearInterpolator.class, OvershootInterpolator.class};
        for (int i = 0; i < interpolatorArr.length; i++) {
            Button button = new Button(activity);
            button.setText(interpolatorArr[i].getSimpleName().replace("Interpolator", ""));
            button.setAllCaps(false);
            llt_move_content.addView(button);
            int finalI = i;
            button.setOnClickListener(v -> {
                TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                        Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_PARENT, 0.87f);
                try {
                    Interpolator in = (Interpolator) interpolatorArr[finalI].newInstance();
                    animation.setInterpolator(in);
                } catch (java.lang.InstantiationException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                animation.setDuration(1000);
                animation.setRepeatMode(Animation.REVERSE);
                animation.setRepeatCount(1);
                button.startAnimation(animation);
            });
        }
    }
}
