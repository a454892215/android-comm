package com.test.util.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.LinearLayout;

import com.common.base.BaseFragment;
import com.test.util.R;
import com.test.util.dialog.CenterDialogFragment;

public class DiaFragFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView(View rootView) {
        LinearLayout tab_layout = rootView.findViewById(R.id.tab_layout);
        int childCount = tab_layout.getChildCount();
        FragmentManager fm = getChildFragmentManager();
        CenterDialogFragment centerDialogFragment = new CenterDialogFragment();
        for (int i = 0; i < childCount; i++) {
            View view = tab_layout.getChildAt(i);
            view.setOnClickListener(v -> centerDialogFragment.show(fm, centerDialogFragment.getClass().getName()));
        }


    }
}
