package com.common.helper;

import android.support.v7.widget.RecyclerView;

import com.common.base.BaseActivity;
import com.common.base.BaseAppRVAdapter;
import com.common.utils.LogUtil;

import java.util.List;
import java.util.Map;

public class AdapterHelpter {

    public static void initAdapter(List<Map<String, String>> list, int layoutId, BaseActivity activity, RecyclerView rv, String classFullName) {
        BaseAppRVAdapter adapter = BaseAppRVAdapter.getInstance(activity, layoutId, list, classFullName);
        rv.setAdapter(adapter);
    }

    public static void notifyAdapterRefresh(List<Map<String, String>> list, RecyclerView rv) {
        BaseAppRVAdapter adapter = (BaseAppRVAdapter) rv.getAdapter();
        if (adapter != null) {
            adapter.getList().clear();
            if (list != null) adapter.getList().addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            LogUtil.e("发生异常：RecyclerView还没有设置Adapter");
        }
    }

    public static void notifyAdapterLoadMore(List<Map<String, String>> list, RecyclerView rv) {
        BaseAppRVAdapter adapter = (BaseAppRVAdapter) rv.getAdapter();
        if (adapter != null && list != null) {
            adapter.getList().addAll(list);
            adapter.notifyDataSetChanged();
        } else {
            LogUtil.e("发生异常：RecyclerView还没有设置Adapter");
        }
    }
}
