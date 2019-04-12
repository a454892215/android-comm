package com.test.util.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;

import com.common.base.BaseAppRVAdapter;
import com.common.helper.DataHelper;
import com.common.helper.RVHelper;
import com.common.utils.FastClickUtil;
import com.test.util.Constant;
import com.test.util.X5WebTestActivity;
import com.test.util.custom_view.CustomViewTestActivity;
import com.test.util.ProcessLiveTestActivity;
import com.test.util.R;
import com.test.util.XposedTestActivity;
import com.test.util.base.BaseAppActivity;

/**
 * Author: L
 * Description:
 */
public class MainActivity extends BaseAppActivity {

    private String[] names = {"View 相关", "Xposed框架", "进程保活", "X5WebView"};
    private Class[] classArr = {CustomViewTestActivity.class, XposedTestActivity.class, ProcessLiveTestActivity.class, X5WebTestActivity.class};


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("API验证");
        RecyclerView rv = findViewById(R.id.recycler_view);
        BaseAppRVAdapter adapter = RVHelper.initVerticalRV(activity, DataHelper.getMapList(names), rv, MainAdapter.class);
        adapter.setOnItemClick((itemView, position) -> {
            if (FastClickUtil.isFastClick()) return;
            Intent intent = new Intent(activity, classArr[position]);
            intent.putExtra(Constant.KEY_HEADER_TITLE, names[position]);
            startActivity(intent);
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
