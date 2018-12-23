package com.common.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class BaseLVAdapter<T> extends BaseAdapter {
    protected BaseActivity activity;
    protected List<T> list = new ArrayList<>();
    private int layoutId;

    public BaseLVAdapter(BaseActivity activity, List<T> list, int layoutId) {
        this.activity = activity;
        this.layoutId = layoutId;
        if (list != null) this.list.addAll(list);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            view = LayoutInflater.from(activity).inflate(layoutId,viewGroup,false);
        }
        return view;
    }
}
