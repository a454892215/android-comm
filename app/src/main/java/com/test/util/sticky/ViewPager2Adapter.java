package com.test.util.sticky;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2020/11/14
 * Description:
 */
public class ViewPager2Adapter extends FragmentStateAdapter {
    List<Fragment> mList = new ArrayList<>();

    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> list) {
        super(fragmentActivity);
        if (list != null) {
            mList.addAll(list);
        }
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mList.get(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
