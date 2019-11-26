package com.lpan.mine.jnitest;

import com.common.utils.ToastUtil;

public class HelloJni {
    static
    {
        System.loadLibrary("helloJni");
    }
    public static native String test();
    public native String testObjMethod();
    public static native int testIntParams(int a,int b);
    public native int[] testIntArrParams(int[] arr);
    public static native String testStringParams(String str);
    public native int testCInvokeJava();

    //该方法在C代码中被调用
    public void test(String str){
        ToastUtil.showShort(str);
    }
}
