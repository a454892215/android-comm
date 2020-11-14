package com.test.util.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Author: Pan
 * 2020/11/14
 * Description:
 */
public abstract class BaseLvAdapter<T> extends BaseAdapter {
    protected List<T> list;
    private int layoutId;

    public BaseLvAdapter(List<T> list, int layoutId) {
        this.list = list;
        this.layoutId = layoutId;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
        onNeedUpdateItem(position, convertView, parent);
        return convertView;
    }

    public abstract void onNeedUpdateItem(int position, View convertView, ViewGroup parent);
}
