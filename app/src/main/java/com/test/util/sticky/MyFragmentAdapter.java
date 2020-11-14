package com.test.util.sticky;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2020/11/14
 * Description:
 */
public class MyFragmentAdapter extends FragmentStatePagerAdapter {
    List<Fragment> list = new ArrayList<>();

    public MyFragmentAdapter(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list.addAll(list);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
