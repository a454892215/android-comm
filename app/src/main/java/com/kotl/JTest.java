package com.kotl;

public class JTest {

    // 在kotlin总不能直接被调用，因为is是kotlin的关键字，需要加上反括号调用
    public static void is(){
        Log.d("我是java中打印的is函数");
    }
}
