package com.test.util.custom_view.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.common.base.BaseFragment;
import com.common.comm.timer.MyCountDownTimer;
import com.common.dialog.BottomDialogFragment;
import com.common.dialog.CenterDialogFragment;
import com.common.helper.FragmentHelper;
import com.common.utils.CastUtil;
import com.common.utils.FastClickUtil;
import com.test.util.R;
import com.common.base.BaseDropDialogFragment;

public class DialogTestFragment_03 extends BaseFragment {

    private Class[] fragmentArr = {CenterDialogFragment.class, BottomDialogFragment.class};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView() {
        BaseDropDialogFragment drop_2 = new BaseDropDialogFragment();
        findViewById(R.id.tv_drop_2).setOnClickListener(anchorView -> drop_2.showAsDropDown(fm,
                drop_2.getClass().getName(), anchorView, 0));

        LinearLayout llt_root = findViewById(R.id.llt_content);
        int childCount = llt_root.getChildCount();
        FragmentManager fm = getChildFragmentManager();
        for (int i = 0; i < childCount; i++) {
            View view = llt_root.getChildAt(i);
            int finalI = i;
            DialogFragment dialogFragment = (DialogFragment) FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[finalI]));//缓存模式 无懒加载
            view.setOnClickListener(v -> dialogFragment.show(fm, fragmentArr[finalI].getName()));
        }

        ProgressBar progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setProgress(100);
        progress_bar.setOnClickListener(v -> {
            FastClickUtil.isFastClick(3000);
            MyCountDownTimer timer = new MyCountDownTimer(100, 30);
            timer.setOnTickListener((time, count) -> progress_bar.setProgress(count));
            timer.start();
        });
    }

}
