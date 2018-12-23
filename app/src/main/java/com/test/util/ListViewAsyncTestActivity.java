package com.test.util;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.common.adapter.TextViewLvAdapter;
import com.common.base.BaseActivity;
import com.common.utils.TestDataUtil;
import com.common.utils.ToastUtil;

/**
 * Author: Pan
 * Description:listView 同步滚动测试
 */
public class ListViewAsyncTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView list_view_1 = findViewById(R.id.list_view_1);
        ListView list_view_2 = findViewById(R.id.list_view_2);
        TextViewLvAdapter adapter_1 = new TextViewLvAdapter(activity, TestDataUtil.getData(100), R.layout.view_tv_1);
        TextViewLvAdapter adapter_2 = new TextViewLvAdapter(activity, TestDataUtil.getData(100), R.layout.view_tv_1);
        list_view_1.setAdapter(adapter_1);
        list_view_2.setAdapter(adapter_2);
        list_view_1.setOnItemClickListener((adapterView, view, position, l) -> {
            ToastUtil.showShort(activity, position + "");
            list_view_1.scrollBy(10, 0);
        });

/*        list_view_1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //list_view_2.scrollTo(0,getScrollY(list_view_1));
              //  list_view_2.setSelection(firstVisibleItem);
              //  list_view_2.scrollTo(0,getScrollY(list_view_1));
                list_view_2.smoothScrollBy(30, 200);
            }
        });*/
    }
    public int getScrollY(ListView listView) {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight() ;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_list_view_test;
    }
}
