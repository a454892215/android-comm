package com.common.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.common.base.BaseFragment;
import com.common.utils.CastUtil;
import com.common.utils.LogUtil;

public class FragmentHelper {


    public static Fragment getInstance(FragmentManager fragmentManager, Class<BaseFragment> fragmentClass) {
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

    private static void showFragment(FragmentManager fragmentManager, Fragment fragment, int id) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(id, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    public static void onSwitchFragment(FragmentManager fragmentManager, Class[] fragmentArr, int position, int contentViewId) {
        FragmentHelper.hideAllShowingFragments(fragmentManager, fragmentArr);
        Fragment fragment = FragmentHelper.getInstance(fragmentManager, CastUtil.cast(fragmentArr[position]));
        FragmentHelper.showFragment(fragmentManager, fragment, contentViewId);
    }
}
