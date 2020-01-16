package com.common.hotfix;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

public interface BaseHotFix {

    void fixActivity(Activity activity, View view);

    void fixFragment(Fragment fragment, View view);
}
