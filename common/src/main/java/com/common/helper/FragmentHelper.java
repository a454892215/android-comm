package com.common.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.common.utils.CastUtil;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;

public class FragmentHelper {


    public static Fragment getInstance(FragmentManager fragmentManager, Class<? extends Fragment> fragmentClass) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        try {
            if (fragment == null) {
                fragment = fragmentClass.newInstance();
            }
        } catch (Exception e) {
            LogUtil.e("反射创建fragment实例异常");
            e.printStackTrace();
        }
        return fragment;
    }

    private static void hideAllShowingFragments(FragmentManager fm, Class[] fragmentArr) {
        for (Class aFragmentArr : fragmentArr) {
            Fragment fragment = fm.findFragmentByTag(aFragmentArr.getName());
            if (fragment != null && fragment.isVisible()) {
                fm.beginTransaction().hide(fragment).commit();
            }
        }
    }

    private static void showFragment(FragmentManager fm, Fragment fragment, int layoutId) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(layoutId, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    private static void addFragment(FragmentManager fm, Fragment fragment, int layoutId) {
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(layoutId, fragment, fragment.getClass().getName()).hide(fragment).commit();
        }
    }

    public static void onSwitchFragment(FragmentManager fm, Class[] fragmentArr, int position, int contentViewId, boolean isPreLoad) {
        FragmentHelper.hideAllShowingFragments(fm, fragmentArr);
        Fragment fragment = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[position]));
        if (isPreLoad) {
            int nextIndex = MathUtil.clamp(position + 1, 0, fragmentArr.length - 1);
            Fragment instance = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[nextIndex]));
            addFragment(fm, instance, contentViewId);
        }
        FragmentHelper.showFragment(fm, fragment, contentViewId);
    }
}
