package com.test.util.main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.common.base.BaseAppRVAdapter;
import com.common.comm.timer.TimerTest;
import com.common.helper.DataHelper;
import com.common.helper.RVHelper;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;
import com.common.utils.ToastUtil;
import com.common.widget.HongBaoYuView;
import com.example.jpushdemo.JGMainActivity;
import com.test.util.Constant;
import com.test.util.JRTTTestActivity;
import com.test.util.JetpackTestActivity;
import com.test.util.BuglyTestActivity;
import com.test.util.QRCodeTestActivity;
import com.test.util.R;
import com.test.util.XposedTestActivity;
import com.test.util.base.MyBaseActivity;
import com.test.util.network.HttpTestActivity;
import com.test.util.custom_view.CustomViewTestActivity;
import com.test.util.sys_notice.NotificationMonitor;
import com.test.util.web_agent.AgentWebActivity;
import com.test.util.x5web.X5WebTestActivity;

/**
 * Author: L
 * Description:
 */
public class MainActivity extends MyBaseActivity {

    private String[] names = {"View 相关", "Xposed框架", "Bugly测试", "X5WebView", "Android Jetpack",
            "http测试", "AgentWeb", "今日头条适配测试和JNI", "极光推送", "二维码保存和自定义模板代码"};
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

        HongBaoYuView hby = findViewById(R.id.hby);

        findViewById(R.id.btn_play).setOnClickListener(v -> hby.play());
        findViewById(R.id.btn_stop).setOnClickListener(v -> hby.stop());
        findViewById(R.id.bt_timer).setOnClickListener(v -> TimerTest.testFPS(activity, 60));

        floatButtonTest(contentView);

        openNotificationListenSettings();

        Intent intent = new Intent(this, NotificationMonitor.class);
        startService(intent);
    }

    public void openNotificationListenSettings() {
        try {
            Intent intent;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
            startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }


    private void floatButtonTest(View contentView) {
        ViewGroup viewGroup = (ViewGroup) contentView;
        View float_btn = LayoutInflater.from(contentView.getContext()).inflate(R.layout.float_btn, viewGroup, false);
        float_btn.setOnClickListener(v -> ToastUtil.showLong("gaga"));
        viewGroup.addView(float_btn);
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
