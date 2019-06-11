package com.common.helper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseActivity;
import com.common.base.BaseAppRVAdapter;
import com.common.utils.LogUtil;

import java.util.List;
import java.util.Map;

public class RVHelper {

    public static BaseAppRVAdapter initVerticalRV(BaseActivity activity, List<?> list, RecyclerView rv, Class<? extends BaseAppRVAdapter> typeClass) {
        BaseAppRVAdapter adapter = BaseAppRVAdapter.getInstance(activity, list, typeClass);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.setAdapter(adapter);
        return adapter;
    }

    public static BaseAppRVAdapter initHorizontalRV(BaseActivity activity, List<?> list, RecyclerView rv, Class<? extends BaseAppRVAdapter> typeClass) {
        BaseAppRVAdapter adapter = BaseAppRVAdapter.getInstance(activity, list, typeClass);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(adapter);
        return adapter;
    }

    public static void notifyAdapterRefresh(List<?> list, RecyclerView rv) {
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
