package com.test.util;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.ViewGroup;
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
        String obj_text = new HelloJni().testObjMethod();
        int add = HelloJni.testIntParams(5, 8);
        String string = HelloJni.testStringParams("我a-bch,嘟嘟嘟");

        int[] arr = new int[]{10, 5, 40, 88};
        arr = new HelloJni().testIntArrParams(arr);
        int result = new HelloJni().testCInvokeJava();

        String info = text
                + "\n" + obj_text
                + "\n" + add
                + "\n" + "经过C处理后的字符串是：" + string
                + "\n" + "经过C处理后的int数组是：" + Arrays.toString(arr)
                + "\n" + "java代码调用C代码，C代码再调用java代码 结果是：" + result;
        tv_jni_info.setText(info);

        testAnim();

    }

    private void testAnim() {
        ViewGroup llt_content_num = findViewById(R.id.llt_content_num);
        llt_content_num.setOnClickListener(v -> {
            for (int i = 0; i < llt_content_num.getChildCount(); i++) {
                int height = llt_content_num.getHeight();
                ValueAnimator animator = ValueAnimator.ofFloat(0, height * 11);
                animator.setDuration(1000);
                int finalI = i;
                animator.addUpdateListener(animation -> {
                    float value = (float) animation.getAnimatedValue();//表示目标距离还剩多少
                    float per_ani_length = value % (height * 2);
                    llt_content_num.getChildAt(finalI).scrollTo(0, Math.round(per_ani_length - height));//[-height,height]
                });
                llt_content_num.postDelayed(animator::start, i * 30);
            }
        });

    }

    public String getText() {
        return BuildConfig.app_info;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jrtt;
    }
}
