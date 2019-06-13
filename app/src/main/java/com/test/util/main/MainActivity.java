package com.test.util.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.helper.DataHelper;
import com.common.helper.RVHelper;
import com.common.utils.FastClickUtil;
import com.test.util.Constant;
import com.test.util.JetpackTestActivity;
import com.test.util.ProcessLiveTestActivity;
import com.test.util.R;
import com.test.util.XposedTestActivity;
import com.test.util.base.BaseAppActivity;
import com.test.util.network.HttpTestActivity;
import com.test.util.custom_view.CustomViewTestActivity;
import com.test.util.x5web.X5WebTestActivity;

/**
 * Author: L
 * Description:
 */
public class MainActivity extends BaseAppActivity {

    private String[] names = {"View 相关", "Xposed框架", "进程保活", "X5WebView", "Android Jetpack", "http测试"};
    private Class[] classArr = {CustomViewTestActivity.class, XposedTestActivity.class,
            ProcessLiveTestActivity.class, X5WebTestActivity.class, JetpackTestActivity.class, HttpTestActivity.class};

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
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
        getWindow().setExitTransition(new Fade().setDuration(1000));//传入新建的变换
        getWindow().setEnterTransition(new Slide().setDuration(1000));
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
