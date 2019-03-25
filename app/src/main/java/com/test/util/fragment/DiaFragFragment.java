package com.test.util.fragment;

import android.view.View;

import com.common.base.BaseFragment;
import com.test.util.R;
import com.test.util.dialog.TestDialogFragment;

public class DiaFragFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView(View rootView) {
        TestDialogFragment testDialogFragment = new TestDialogFragment();
        rootView.findViewById(R.id.btn).setOnClickListener(v -> {
            testDialogFragment.show(getChildFragmentManager(), testDialogFragment.getClass().getName());
        });
    }
}
