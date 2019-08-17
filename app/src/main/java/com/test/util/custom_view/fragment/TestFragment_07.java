package com.test.util.custom_view.fragment;

import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.View;
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
        Scene scene_1 = Scene.getSceneForLayout(flt_content, R.layout.layout_scene_1, activity);
        Scene scene_2 = Scene.getSceneForLayout(flt_content, R.layout.layout_scene_2, activity);
        TransitionManager.go(scene_1, new ChangeBounds());
        btn_1.setOnClickListener(v -> {
            Scene targetScene = count++ % 2 == 0 ? scene_2 : scene_1;
            TransitionManager.go(targetScene, new ChangeBounds());
        });
    }
}
