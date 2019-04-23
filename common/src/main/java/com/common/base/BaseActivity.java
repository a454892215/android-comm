package com.common.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.common.R;
import com.common.helper.PopWindowHelper;
import com.common.listener.OnBackPressedListener;
import com.common.utils.DensityUtils;

/**
 * Author:  L
 * Description: No
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity activity = this;
    private boolean isShowing;
    protected FragmentManager fm;
    public static int bottomVirtualKeyHeight;
    private View contentView;
    public float dp_1;
    protected boolean isSetLayoutId = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        if (isSetLayoutId) setContentView(getLayoutId());
        contentView = findViewById(android.R.id.content);
        dp_1 = getResources().getDimension(R.dimen.dp_1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
    }

    public boolean isShowing() {
        return isShowing;
    }

    protected abstract int getLayoutId();

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setBottomVirtualKeyHeight();
    }


    private void setBottomVirtualKeyHeight() {
        int contentViewHeight = contentView.getHeight();
        int realScreenHeight = DensityUtils.getRealScreenHeight(this);
        bottomVirtualKeyHeight = realScreenHeight - contentViewHeight;
        if (bottomVirtualKeyHeight > dp_1 * 100) bottomVirtualKeyHeight = Math.round(dp_1 * 100);
    }

    protected PopupWindow loadingPop;

    public void showDefaultLoadingPop() {
        if (loadingPop == null) {
            @SuppressLint("InflateParams") View pop_view = LayoutInflater.from(activity).inflate(R.layout.common_loading_layout, null);
            loadingPop = PopWindowHelper.getPopWindow(pop_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        }
        PopWindowHelper.show(this, loadingPop, Gravity.CENTER);
    }

    public void dismissDefaultLoadingPop() {
        PopWindowHelper.dismissPop(loadingPop);
    }

    private OnBackPressedListener onBackPressedListener;

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null && onBackPressedListener.onBack()) return;
        super.onBackPressed();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }
}
