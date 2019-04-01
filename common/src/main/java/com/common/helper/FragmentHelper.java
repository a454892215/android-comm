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

    private static void hideAllShowingFragments(FragmentManager fragmentManager, Class[] fragmentArr) {
        for (Class aFragmentArr : fragmentArr) {
            Fragment fragment = fragmentManager.findFragmentByTag(aFragmentArr.getName());
            if (fragment != null && fragment.isVisible()) {
                fragmentManager.beginTransaction().hide(fragment).commit();
            }
        }
    }

    private static void showFragment(FragmentManager fragmentManager, Fragment fragment, int layoutId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(layoutId, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    private static void addFragment(FragmentManager fragmentManager, Fragment fragment, int layoutId) {
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(layoutId, fragment, fragment.getClass().getName()).hide(fragment).commit();
        }
    }

    public static void onSwitchFragment(FragmentManager fragmentManager, Class[] fragmentArr, int position, int contentViewId, boolean isPreLoad) {
        FragmentHelper.hideAllShowingFragments(fragmentManager, fragmentArr);
        Fragment fragment = FragmentHelper.getInstance(fragmentManager, CastUtil.cast(fragmentArr[position]));
        if (isPreLoad) {
            int nextIndex = MathUtil.clamp(position + 1, 0, fragmentArr.length - 1);
            Fragment instance = FragmentHelper.getInstance(fragmentManager, CastUtil.cast(fragmentArr[nextIndex]));
            addFragment(fragmentManager, instance, contentViewId);
        }
        FragmentHelper.showFragment(fragmentManager, fragment, contentViewId);
    }
}
