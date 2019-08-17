package com.test.util.custom_view.fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.common.base.BaseFragment;
import com.test.util.R;

public class TestFragment_07 extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_flow_layout;
    }

    private int count = 0;

    @Override
    protected void initView() {
        FrameLayout flt_content = findViewById(R.id.flt_content);
        View btn_1 = findViewById(R.id.btn_1);
        testSceneSwitch3(flt_content, btn_1);
    }


    @SuppressWarnings("unused")
    private void testSceneSwitch3(FrameLayout flt_content, View btn_1) {
        btn_1.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(flt_content);
            if (count++ % 2 == 0) {
                flt_content.setBackground(new ColorDrawable(Color.parseColor("#ffffff")));
            } else {
                flt_content.setBackground(new ColorDrawable(Color.parseColor("#ff0000")));
            }
        });
    }


    @SuppressWarnings("unused")
    private void testSceneSwitch2(FrameLayout flt_content, View btn_1) {
        btn_1.setOnClickListener(v -> {
            TransitionManager.beginDelayedTransition(flt_content, new ChangeTransform());
            ViewGroup.LayoutParams lp = flt_content.getLayoutParams();
            if (count++ % 2 == 0) {
                lp.width = Math.round(dp_1 * 100);
                lp.height = Math.round(dp_1 * 100);
            } else {
                lp.width = Math.round(dp_1 * 300);
                lp.height = Math.round(dp_1 * 300);
            }
            flt_content.setLayoutParams(lp);
        });
    }

    @SuppressWarnings("unused")
    private void testSceneSwitch(FrameLayout flt_content, View btn_1) {
        Scene scene_1 = Scene.getSceneForLayout(flt_content, R.layout.layout_scene_1, activity);
        Scene scene_2 = Scene.getSceneForLayout(flt_content, R.layout.layout_scene_2, activity);
        TransitionManager.go(scene_1, new ChangeBounds());
        btn_1.setOnClickListener(v -> {
            Scene targetScene = count++ % 2 == 0 ? scene_2 : scene_1;
            TransitionManager.go(targetScene, new ChangeBounds());
        });
    }
}
