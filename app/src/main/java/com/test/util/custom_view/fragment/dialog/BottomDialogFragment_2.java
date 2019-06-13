package com.test.util.custom_view.fragment.dialog;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.common.R;
import com.common.base.BaseDialogFragment;

public class BottomDialogFragment_2 extends BaseDialogFragment {

    private View llt_content;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGravity(Gravity.BOTTOM);
        setHeight(match_parent);
        setWidth(match_parent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_fra_test_2;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        startAnim();
    }

    private void startAnim() {
        if (llt_content != null) {
            llt_content.setTranslationY(llt_content.getTranslationY() + 800);
            llt_content.animate()
                  //  .setInterpolator(new DeceInterpolator())
                    .setDuration(300).translationYBy(-800).start();
        }
    }

    @Override
    protected void initView() {
        llt_content = findViewById(R.id.llt_content);
        startAnim();
    }
}
