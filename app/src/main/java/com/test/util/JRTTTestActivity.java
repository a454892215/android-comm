package com.test.util;

import android.os.Bundle;
import android.widget.TextView;

import com.lpan.mine.jnitest.HelloJni;
import com.test.util.base.BaseAppActivity;

import java.util.Arrays;

public class JRTTTestActivity extends BaseAppActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv_jni_info = findViewById(R.id.tv_jni_info);
        //JNI方法测试
        String text = HelloJni.test();
        String  obj_text= new HelloJni().testObjMethod();
        int add = HelloJni.testIntParams(5, 8);
        String string = HelloJni.testStringParams("我a-bch,嘟嘟嘟");

        int[] arr = new int[]{10,5,40,88};
        arr = new HelloJni().testIntArrParams(arr);
        int result = new HelloJni().testCInvokeJava();

        String info = text
                +"\n"+obj_text
                +"\n"+add
                +"\n"+"经过C处理后的字符串是："+string
                +"\n"+ "经过C处理后的int数组是："+ Arrays.toString(arr)
                +"\n"+"java代码调用C代码，C代码再调用java代码 结果是："+result;
        tv_jni_info.setText(info);

    }

    public String getText() {
        return BuildConfig.app_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jrtt;
    }
}
