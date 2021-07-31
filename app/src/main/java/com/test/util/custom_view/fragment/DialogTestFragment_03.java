package com.test.util.custom_view.fragment;

import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.fragment.app.DialogFragment;

import com.common.base.BaseFragment;
import com.common.comm.timer.MyTimer;
import com.common.dialog.BottomDialogFragment;
import com.common.dialog.CenterDialogFragment;
import com.common.dialog.LoadingPopWindow;
import com.common.helper.FragmentHelper;
import com.common.utils.CastUtil;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;
import com.test.util.App;
import com.test.util.R;
import com.common.base.BaseDropDialogFragment;

public class DialogTestFragment_03 extends BaseFragment {

    private final Class<?>[] fragmentArr = {CenterDialogFragment.class, BottomDialogFragment.class};
    private MyTimer timer;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dia_fra;
    }

    @Override
    protected void initView() {
        BaseDropDialogFragment drop_2 = new BaseDropDialogFragment(activity);
        findViewById(R.id.tv_drop_2).setOnClickListener(anchorView -> {
            App app = (App) activity.getApplication();
            app.soundPoolUtil.play(0);
            drop_2.showAsDropDown(anchorView, 0, 0);
        });

        findViewById(R.id.btn_center_dialog).setOnClickListener(v -> {
            DialogFragment dialogFragment = (DialogFragment) FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[0]));//缓存模式 无懒加载
            dialogFragment.show(fm, fragmentArr[0].getName());
        });

        findViewById(R.id.btn_bottom_dialog).setOnClickListener(v -> {
            DialogFragment dialogFragment = (DialogFragment) FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[1]));//缓存模式 无懒加载
            dialogFragment.show(fm, fragmentArr[1].getName());
        });

        findViewById(R.id.loading_fragment_test).setOnClickListener(v -> {
            if (activity.isShowLoadingDialogFragment()) {
                activity.dismissDefaultLoadingView();
            } else {
                activity.showDefaultLoadingView();
                v.postDelayed(() -> activity.dismissDefaultLoadingView(), 2000);
            }
        });
        findViewById(R.id.loading_pop_window).setOnClickListener(v -> {
            LoadingPopWindow loadingPopWindow = new LoadingPopWindow(activity);
            loadingPopWindow.show();
            v.postDelayed(loadingPopWindow::dismiss, 2000);
        });

        ProgressBar progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setProgress(100);
        progress_bar.setOnClickListener(v -> {
            FastClickUtil.isFastClick(3000);
            if (timer != null) timer.cancel();
            timer = new MyTimer(3000, 30);
            timer.setOnTickListener((time, count) -> {
                LogUtil.d("=======count：" + count + "  time:" + (time / 1000f));
                progress_bar.setProgress(count);
            });
            timer.start();
        });

        initSeekBar(findViewById(R.id.seek_bar));
    }

    private void initSeekBar(SeekBar seek_bar_bg_music) {
        seek_bar_bg_music.setMax(50);
        seek_bar_bg_music.setProgress(100);
        seek_bar_bg_music.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

}
