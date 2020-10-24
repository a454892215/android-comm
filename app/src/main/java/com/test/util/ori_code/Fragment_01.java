package com.test.util.ori_code;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.common.base.BaseFragment;
import com.test.util.R;

import java.lang.ref.WeakReference;

public class Fragment_01 extends BaseFragment {

    private TextView textInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.ori_code_fragment_1;
    }

    //写法1： handler1 handler2 的区别是什么？
    Handler handler1 = new Handler(new Handler.Callback() {
        @SuppressLint("SetTextI18n")
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 1) {
                textInfo.setText("handler1收到了信息： what:" + msg.what);
            }
            return false;
        }
    });

    /**
     * /写法2： 匿名内部类写法 编译器有内存泄露警告
     * 此时即使把 handler2 设为static也依然会有内存泄露警告（问题？ 既然设置为static 并且编译通过，那么Handler是不会依赖外部对象的，
     * 因为静态成员初始化优先于对象，为什么还会有内存泄露警告？）
     */
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {
                textInfo.setText("handler2收到了信息： what:" + msg.what);
            }
        }
    };


    //写法3：静态内部类写法（编译器无警告，静态内部类不依赖外部类的对象）
    private static class MyHandle extends Handler {
        WeakReference<Fragment_01> weakReference;

        public MyHandle(Fragment_01 fragment_01) {
            this.weakReference = new WeakReference<>(fragment_01);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 6) {
                Fragment_01 fragment_01 = weakReference.get();
                if (fragment_01 != null) {
                    fragment_01.textInfo.setText("handler3收到了信息： what:" + msg.what);
                }

            }
        }
    }

    @Override
    protected void initView() {

        textInfo = findViewById(R.id.text_info);
        findViewById(R.id.btn_1).setOnClickListener(v -> {
            handler1.sendEmptyMessage(1);
        });

        findViewById(R.id.btn_2).setOnClickListener(v -> {
            handler2.sendEmptyMessage(3);
        });

        findViewById(R.id.btn_3).setOnClickListener(v -> {
            new MyHandle(this).sendEmptyMessage(6);
        });
    }

}
