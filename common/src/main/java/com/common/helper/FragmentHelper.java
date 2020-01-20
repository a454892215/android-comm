package com.common.helper;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.common.CommApp;
import com.common.utils.CastUtil;
import com.common.utils.LogUtil;

public class FragmentHelper {

    private FragmentManager fm;
    private int contentViewId;
    private SparseArray<Fragment> fragmentInstanceArr = new SparseArray<>();

    public FragmentHelper(FragmentManager fm, Class[] fragmentArr, int contentViewId) {
        this.fm = fm;
        this.contentViewId = contentViewId;
        for (int i = 0; i < fragmentArr.length; i++) {
            Fragment fragment = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[i]));
            fragmentInstanceArr.put(i, fragment);
        }
    }


    public static Fragment getInstance(FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        try {
            if (fragment == null) {
                fragment = fragmentClass.newInstance();
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return fragment;
    }


    public void onSwitchFragment(int position) {
        for (int i = 0; i < fragmentInstanceArr.size(); i++) {
            Fragment fragment = fragmentInstanceArr.get(i);
            if (fragment.isVisible()) {
                fm.beginTransaction().hide(fragment).commit();
            }
        }
        Fragment showingFragment = fragmentInstanceArr.get(position);
        if (showingFragment.isAdded()) {
            fm.beginTransaction().show(showingFragment).commit();
        } else {
            fm.beginTransaction().add(contentViewId, showingFragment, showingFragment.getClass().getName()).show(showingFragment).commit();
        }
        CommApp.getHotFixCallback().onSwitchShowFragment(showingFragment);
        currentPosition = position;
    }

    public SparseArray<Fragment> getAllFragment() {
        return fragmentInstanceArr;
    }

    private int currentPosition = 0;

    public int getCurrentPosition() {
        return currentPosition;
    }
}
