package com.common.helper;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.common.utils.CastUtil;
import com.common.utils.LogUtil;

public class FragmentHelper {

    private FragmentManager fm;

    private SparseArray<Fragment> fragmentInstanceArr = new SparseArray<>();

    public FragmentHelper(FragmentManager fm, Class[] fragmentArr, int contentViewId) {
        this.fm = fm;
        for (int i = 0; i < fragmentArr.length; i++) {
            Fragment fragment = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[i]));
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(contentViewId, fragment, fragment.getClass().getName()).hide(fragment).commit();
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
            fm.beginTransaction().hide(fragmentInstanceArr.get(i)).commit();
        }
        fm.beginTransaction().show(fragmentInstanceArr.get(position)).commit();
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
