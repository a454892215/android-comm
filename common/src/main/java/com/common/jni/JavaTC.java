package com.common.jni;

/**
 *  JDK版本 1.8
 *  步骤1：生成该类的.class文件 执行命令：javac Java2C.java (重点：该文件目录下执行cmd命令)
 *  步骤2：生成class文件的头文件 javah -jni com.common.jni.JavaTC （重点：该class内包目录的上级目录下执行cmd命令）
 */
public class JavaTC {
    static {
        System.loadLibrary("MyJniLib");
    }

    
    public native String getText(String text);
}