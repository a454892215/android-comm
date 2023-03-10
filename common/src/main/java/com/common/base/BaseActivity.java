package com.common.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.common.dialog.LoadingPopWindow;
import com.common.listener.OnBackPressedListener;
import com.common.listener.OnRequestPermissionFinish;
import com.common.utils.DensityMatcherUtil;
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
    public View contentView;
    protected boolean isSetLayoutId = true;
    private BasePopWindow loadingWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  SystemUtils.hideBottomVirtualKey(activity);
        //设置允许通过ActivityOptions.makeSceneTransitionAnimation发送或者接收Bundle
        DensityMatcherUtil.onActivityCreate(this, 360, false);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        //设置使用TransitionManager进行动画，不设置的话系统会使用一个默认的TransitionManager
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        fm = getSupportFragmentManager();
        if (isSetLayoutId) setContentView(getLayoutId());
        contentView = findViewById(android.R.id.content);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
        for (int i = 0, size = onPauseListenerList.size(); i < size; i++) {
            OnPauseListener onPauseListener = onPauseListenerList.get(i);
            if (onPauseListener != null) onPauseListener.onPause();
        }
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

    public boolean isShowLoadingDialogFragment() {
        return loadingWindow != null && loadingWindow.isShowing();
    }


    public void showDefaultLoadingView() {
        if (loadingWindow == null) {
            loadingWindow = new LoadingPopWindow(this);
        }
        runOnUiThread(()-> loadingWindow.show());
    }

    public void dismissDefaultLoadingView() {
        if (loadingWindow != null && loadingWindow.isShowing()) {
            runOnUiThread(()-> loadingWindow.dismiss());

        }
    }

    private final List<OnBackPressedListener> onBackPressedListenerList = new ArrayList<>();

    @Override
    public final void onBackPressed() {
        if (onBackPressedListenerList.size() > 0) {
            for (int i = 0; i < onBackPressedListenerList.size(); i++) {
                if (onBackPressedListenerList.get(i).onBack()) return;
            }
        }
        super.onBackPressed();
    }

    public void addOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        onBackPressedListenerList.add(onBackPressedListener);
    }


    ArrayList<OnPauseListener> onPauseListenerList = new ArrayList<>();

    public void addOnPauseListener(OnPauseListener onPauseListener) {
        onPauseListenerList.add(onPauseListener);
    }

    public interface OnPauseListener {
        void onPause();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
     /*   if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            SystemUtils.hideBottomVirtualKey(activity);
            SystemUtils.hideSoftKeyboard(activity);
            getWindow().getDecorView().requestFocus();
        }*/
        return super.dispatchTouchEvent(ev);

    }

    OnRequestPermissionFinish onRequestPermissionFinish;

    public void setOnRequestPermissionFinish(OnRequestPermissionFinish onRequestPermissionFinish) {
        this.onRequestPermissionFinish = onRequestPermissionFinish;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (onRequestPermissionFinish != null) {
            onRequestPermissionFinish.onFinish(grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }
}
