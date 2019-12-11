package com.test.util.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.view.KeyEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.comm.timer.MyTimer;
import com.common.helper.DataHelper;
import com.common.helper.RVHelper;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;
import com.example.jpushdemo.JGMainActivity;
import com.test.util.Constant;
import com.test.util.JRTTTestActivity;
import com.test.util.JetpackTestActivity;
import com.test.util.BuglyTestActivity;
import com.test.util.QRCodeTestActivity;
import com.test.util.R;
import com.test.util.XposedTestActivity;
import com.test.util.base.BaseAppActivity;
import com.test.util.network.HttpTestActivity;
import com.test.util.custom_view.CustomViewTestActivity;
import com.test.util.web_agent.AgentWebActivity;
import com.test.util.x5web.X5WebTestActivity;

/**
 * Author: L
 * Description:
 */
public class MainActivity extends BaseAppActivity {

    private String[] names = {"View 相关", "Xposed框架", "Bugly测试", "X5WebView", "Android Jetpack",
            "http测试", "AgentWeb", "今日头条适配测试和JNI", "极光推送", "二维码保存等"};
    private Class[] classArr = {CustomViewTestActivity.class, XposedTestActivity.class,
            BuglyTestActivity.class, X5WebTestActivity.class, JetpackTestActivity.class,
            HttpTestActivity.class, AgentWebActivity.class, JRTTTestActivity.class, JGMainActivity.class,
            QRCodeTestActivity.class};

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


        findViewById(R.id.bt_timer).setOnClickListener(v -> {
            MyTimer myTimer = new MyTimer(1000, 100);
            myTimer.setOnTickListener((millisUntilFinished, count) -> LogUtil.d("===================:" + millisUntilFinished / 1000f + "  count:" + count));
            myTimer.start();
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
