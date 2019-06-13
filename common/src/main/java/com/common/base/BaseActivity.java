package com.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.common.R;
import com.common.dialog.LoadingDialogFragment;
import com.common.listener.OnBackPressedListener;
import com.common.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:  L
 * Description: No
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity activity = this;
    private boolean isShowing;
    protected FragmentManager fm;
    public static int bottomVirtualKeyHeight;
    public static int contentViewHeight;
    private View contentView;
    public float dp_1;
    protected boolean isSetLayoutId = true;
    private LoadingDialogFragment loadingDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置允许通过ActivityOptions.makeSceneTransitionAnimation发送或者接收Bundle
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
       //设置使用TransitionManager进行动画，不设置的话系统会使用一个默认的TransitionManager
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
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
        computeHeight();
    }


    private void computeHeight() {
        contentViewHeight = contentView.getHeight();
        int realScreenHeight = DensityUtils.getRealScreenHeight(this);
        int[] location_content = new int[2];
        contentView.getLocationOnScreen(location_content);
        bottomVirtualKeyHeight = realScreenHeight - contentViewHeight - location_content[1];
    }


    public void showDefaultLoadingView() {
        if (loadingDialogFragment == null) {
            loadingDialogFragment = new LoadingDialogFragment();
        }
        loadingDialogFragment.show(fm, loadingDialogFragment.getClass().getName());
    }

    public void dismissDefaultLoadingView() {
        loadingDialogFragment.dismiss();
    }

    private List<OnBackPressedListener> onBackPressedListenerList = new ArrayList<>();

    @Override
    public final void onBackPressed() {
        if (onBackPressedListenerList.size() > 0) {
            for (int i = 0; i < onBackPressedListenerList.size(); i++) {
              if(onBackPressedListenerList.get(i).onBack()) return;
            }
        }
        super.onBackPressed();
    }

    public void addOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        onBackPressedListenerList.add(onBackPressedListener);
    }
}
