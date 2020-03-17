package com.test.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.common.helper.AdapterHelper;
import com.test.util.base.MyBaseActivity;
import com.test.util.sys_notice.NoticeAdapter;

import java.util.ArrayList;

public class JetpackTestActivity extends MyBaseActivity {

    public static final String ACTION_NOTICE = "sys.NoticeReceiver";
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rv = findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        NoticeAdapter adapter = new NoticeAdapter(activity, null);
        rv.setAdapter(adapter);

        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NOTICE);
        JetpackTestActivity.NoticeReceiver noticeReceiver = new JetpackTestActivity.NoticeReceiver();
        registerReceiver(noticeReceiver, filter);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_jetpack_test;
    }
  private static ArrayList<Object> list = new ArrayList<>();
    /**
     * Author: Pan
     * 2020/3/17
     * Description:
     */
    public class NoticeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("content");
            list.add(title + "\r\n" + text + "\r\n\r\n");
            AdapterHelper.notifyAdapterLoadMore(list, rv);
        }
    }
}
