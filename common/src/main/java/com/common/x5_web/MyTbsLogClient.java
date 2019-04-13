package com.common.x5_web;

import android.content.Context;
import android.widget.TextView;

import com.common.utils.LogUtil;
import com.tencent.smtt.utils.TbsLogClient;

public class MyTbsLogClient extends TbsLogClient {
    public MyTbsLogClient(Context context) {
        super(context);
    }

    @Override
    public void writeLog(String s) {
        super.writeLog(s);
    }

    @Override
    public void writeLogToDisk() {
        super.writeLogToDisk();
    }

    @Override
    public void showLog(String s) {
        super.showLog(s);
    }

    @Override
    public void setLogView(TextView textView) {
        super.setLogView(textView);
    }

    @Override
    public void i(String s, String s1) {
        super.i(s, s1);
        LogUtil.d("==========s:" + s + "          s1:" + s1);
    }

    @Override
    public void e(String s, String s1) {
        super.e(s, s1);
        LogUtil.e("==========s:" + s + "          s1:" + s1);
    }

    @Override
    public void w(String s, String s1) {
        super.w(s, s1);
        LogUtil.d("==========s:" + s + "          s1:" + s1);
    }

    @Override
    public void d(String s, String s1) {
        super.d(s, s1);
        LogUtil.d("==========s:" + s + "          s1:" + s1);
    }

    @Override
    public void v(String s, String s1) {
        super.v(s, s1);
        LogUtil.d("==========s:" + s + "          s1:" + s1);
    }
}
