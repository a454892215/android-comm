package com.test.util.ori_code;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.common.base.BaseFragment;
import com.common.utils.LogUtil;
import com.test.util.R;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;

/**
 * Handler验证
 */
public class Fragment_01 extends BaseFragment {

    private TextView textInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.ori_code_fragment_1;
    }


    /**
     * /写法1： 匿名内部类写法 （编译器有内存泄露警告）
     * 此时即使把 handler1 设为static也依然会有内存泄露警告（问题？ 既然设置为static 并且编译通过，
     * 那么匿名内部类对象Handler1是不会依赖外部对象的，
     * 因为静态成员初始化优先于对象，为什么还会有内存泄露警告？是编译器警告的bug?）
     */
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "handler1收到了信息： what:" + msg.what;
            LogUtil.d(text + " this:" + textInfo);
            textInfo.setText(text);
        }
    };


    /**
     * 写法2：（编译器无内存泄露警告） handler1 handler2 的区别是什么, 为什么写法2是正常的，没有内存泄露警告？
     * handle之所以会内存泄露 是因为如果不主动销毁，其正常的生命周期会比外部类长，而其如果持有外部类对象，
     * 会导致外部类对象不能及时回收。此种写法，直接持有外部类对象的是Handler.Callback，而非handler2??
     */
    private Handler handler2 = new Handler(new Handler.Callback() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(Message msg) {
            String text = "handler2收到了信息： what:" + msg.what;
            LogUtil.d(text + " this:" + textInfo);
            textInfo.setText(text);
            return false;
        }
    });

    /**
     * 写法3：静态内部类写法（编译器无警告，静态内部类不依赖外部类的对象）
     */
    private static class MyHandle extends Handler {
        WeakReference<Fragment_01> weakReference;

        public MyHandle(Fragment_01 fragment_01) {
            this.weakReference = new WeakReference<>(fragment_01);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String text = "handler3收到了信息： what:" + msg.what;

            Fragment_01 fragment_01 = weakReference.get();
            LogUtil.d(text + " this:" + fragment_01);
            if (fragment_01 != null) {
                fragment_01.textInfo.setText(text);
            }
        }
    }

    @Override
    protected void initView() {
        textInfo = findViewById(R.id.text_info);
        Executors.newSingleThreadExecutor().execute(() -> {
            //即时消息测试
            findViewById(R.id.btn_1).setOnClickListener(v -> handler1.sendEmptyMessage(1));
            findViewById(R.id.btn_2).setOnClickListener(v -> handler2.sendEmptyMessage(2));
            findViewById(R.id.btn_3).setOnClickListener(v -> new MyHandle(this).sendEmptyMessage(3));

            //延迟消息测试 内存泄露问题
            findViewById(R.id.btn_1_test).setOnClickListener(v -> handler1.sendEmptyMessageDelayed(11, 3000));
            findViewById(R.id.btn_2_test).setOnClickListener(v -> handler2.sendEmptyMessageDelayed(22, 3000));
            findViewById(R.id.btn_3_test).setOnClickListener(v -> new MyHandle(this).sendEmptyMessageDelayed(33, 3000));
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d("========onDestroy=============");
    }
}
