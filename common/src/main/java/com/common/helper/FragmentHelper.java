package com.common.helper;

import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.common.utils.CastUtil;
import com.common.utils.LogUtil;
import com.common.utils.MathUtil;

public class FragmentHelper {

    private FragmentManager fm;
    private Class[] fragmentArr;
    private int contentViewId;

    private SparseArray<Fragment> fragmentInstanceArr = new SparseArray<>();

    public FragmentHelper(FragmentManager fm, Class[] fragmentArr, int contentViewId) {
        this.fm = fm;
        this.fragmentArr = fragmentArr;
        this.contentViewId = contentViewId;
    }


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

    private void hideAllShowingFragments(FragmentManager fm, Class[] fragmentArr) {
        for (Class aFragmentArr : fragmentArr) {
            Fragment fragment = fm.findFragmentByTag(aFragmentArr.getName());
            if (fragment != null && fragment.isVisible()) {
                fm.beginTransaction().hide(fragment).commit();
            }
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(contentViewId, fragment, fragment.getClass().getName());
        }
        transaction.commit();
    }

    private void addFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.add(contentViewId, fragment, fragment.getClass().getName()).hide(fragment).commit();
        }
    }

    public void onSwitchFragment(int position, boolean isPreLoad) {
        hideAllShowingFragments(fm, fragmentArr);
        Fragment fragment = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[position]));
        fragmentInstanceArr.put(position, fragment);
        if (isPreLoad) {
            int nextIndex = MathUtil.clamp(position + 1, 0, fragmentArr.length - 1);
            Fragment instance = FragmentHelper.getInstance(fm, CastUtil.cast(fragmentArr[nextIndex]));
            addFragment(instance);
            fragmentInstanceArr.put(nextIndex, instance);
        }
        showFragment(fragment);
    }

    public SparseArray<Fragment> getFragmentInstanceArr() {
        return fragmentInstanceArr;
    }
}
