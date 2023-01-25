package com.test.util.main;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;

import androidx.recyclerview.widget.RecyclerView;

import com.aidl.AidlTestActivity;
import com.common.base.BaseAppRVAdapter;
import com.common.comm.timer.TimerTest;
import com.common.helper.DataHelper;
import com.common.helper.RVHelper;
import com.common.utils.AssertsUtil;
import com.common.utils.FastClickUtil;
import com.common.utils.LogUtil;
import com.common.utils.SystemRingUtil;
import com.common.widget.HongBaoYuView;
import com.example.jpushdemo.JGMainActivity;
import com.test.util.App;
import com.test.util.Constant;
import com.test.util.JRTTAndJNITestActivity;
import com.test.util.accessibility.AccessibilityActivity;
import com.test.util.accessibility.AccessibilityVerifyActivity;
import com.test.util.custom_view2.CustomViewTestActivity2;
import com.test.util.ori_code.OriCodeActivity;
import com.test.util.sticky.StickyTestActivity;
import com.test.util.sticky.DatabaseTestActivity;
import com.test.util.sys_notice.JetpackTestActivity;
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

import java.util.Date;
import java.util.concurrent.Executors;

/**
 * Author: L
 * Description:
 */
@SuppressWarnings("unused")
public class MainActivity extends MyBaseActivity {

    private final String[] names = {"源码验证", "View 相关", "View 相关2", "Xposed框架", "Bugly和各种异常捕获", "X5WebView", "Android Jetpack|通知监听",
            "http测试", "AgentWeb", "今日头条适配测试和JNI", "极光推送", "二维码保存和自定义模板代码", "吸顶效果", "数据库测试", "AIDL", "辅助功能", "辅助功能测试页"};
    private final Class<?>[] classArr = {OriCodeActivity.class, CustomViewTestActivity.class, CustomViewTestActivity2.class, XposedTestActivity.class,
            BuglyTestActivity.class, X5WebTestActivity.class, JetpackTestActivity.class,
            HttpTestActivity.class, AgentWebActivity.class, JRTTAndJNITestActivity.class, JGMainActivity.class,
            QRCodeTestActivity.class, StickyTestActivity.class, DatabaseTestActivity.class, AidlTestActivity.class,
            AccessibilityActivity.class, AccessibilityVerifyActivity.class};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setTitle("API验证");
            RecyclerView rv = findViewById(R.id.recycler_view);
            BaseAppRVAdapter adapter = RVHelper.initVerticalRV(activity, DataHelper.getMapList(names), rv, MainAdapter.class);
            adapter.setOnItemClick((itemView, position) -> {
                if (FastClickUtil.isFastClick()) return;
                Intent intent = new Intent(activity, classArr[position]);
                intent.putExtra(Constant.KEY_HEADER_TITLE, names[position]);
                // startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(intent);
            });

            HongBaoYuView hby = findViewById(R.id.hby);
            findViewById(R.id.btn_play).setOnClickListener(v -> hby.play());
            findViewById(R.id.btn_stop).setOnClickListener(v -> {
                App.app.soundPoolUtil.play(0);
                hby.stop();
            });
            findViewById(R.id.bt_timer).setOnClickListener(v -> TimerTest.testFPS(activity, 60));
            findViewById(R.id.btn_last).setOnClickListener(v -> SystemRingUtil.getInstance().playLast(App.app));
            findViewById(R.id.btn_next).setOnClickListener(v -> SystemRingUtil.getInstance().playNext(App.app));
            findViewById(R.id.btn_stop_play_ring).setOnClickListener(v -> {
                // SystemRingUtil.getInstance().stopRecentRing();
                Executors.newSingleThreadExecutor().execute(this::compute);
            });
            //floatButtonTest(contentView);

            // openNotificationListenSettings();
            String text = AssertsUtil.getText(this, "china_city.txt");
            findViewById(R.id.btn_test).setOnClickListener(v -> LogUtil.d(text));

            startMonitor();
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private void compute() {
        LogUtil.d(" 开始 cost time:");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            /*for (int j = 0; j < 1000; j++) {

            }*/
            Date date = new Date();
        }
        long end = System.currentTimeMillis();
        long t = end - start;
        // System.out.println("  cost time:" + t);
        LogUtil.d("  cost time:" + t);
    }

    private void startMonitor() {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
