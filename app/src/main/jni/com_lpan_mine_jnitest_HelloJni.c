//
// Created by Administrator on 2018/3/10.
//
#include <jni.h>
#include "com_lpan_mine_jnitest_HelloJni.h"
#include <stdlib.h>
#include <string.h>
#include <android/log.h>
#define LOG_TAG "ll_pp===="
//...表示可变参数列表，__VA_ARGS__在预处理中，会被实际的参数集（实参列表）所替换。
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
//#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

/*
 * 根据java 的native类生成其 jni头文件的命令：因为.h头文件是根据.class类生成  所以命令中要指定 -classpath
 * javah -d E:\works\projects\PrivateProject\app\src\main\jni -classpath E:\works\projects\PrivateProject\app\build\intermediates\classes\debug com.lpan.mine.jnitest.HelloJni

 *c本地函数命名规则  Java_包名_类名_本地方法名
 *JNIEnv* env 是结构体JNINativeInterface 的二级指针
 *JNIEnv 是结构体JNINativeInterface 的一级指针
 *JNINativeInterface结构体中定义了大量的函数指针 这些函数指针在jni开发中很常用
 *jobject  调用本地函数的java对象
*/
//java静态本地方法
JNIEXPORT jstring JNICALL Java_com_lpan_mine_jnitest_HelloJni_test
        (JNIEnv *env, jclass jcls) {
    return (*env)->NewStringUTF(env, "hello, I am from C--jclass");
}

//java对象的本地方法
JNIEXPORT jstring JNICALL Java_com_lpan_mine_jnitest_HelloJni_testObjMethod
        (JNIEnv *env, jobject jobj) {
    return (*env)->NewStringUTF(env, "hello, I am from C--jobject");
}

//int类型传参
JNIEXPORT jint JNICALL Java_com_lpan_mine_jnitest_HelloJni_testIntParams
        (JNIEnv *env, jclass jcls, jint a, jint b) {
    return a + b;
}

//int数组传参：jintArray的一般处理方式
JNIEXPORT jintArray JNICALL Java_com_lpan_mine_jnitest_HelloJni_testIntArrParams
        (JNIEnv *env, jobject obj, jintArray jArray){
    jsize length =(*env)->GetArrayLength(env,jArray);
    LOGD("length = %d",length);
    jint* arr_pointer =(*env)->GetIntArrayElements(env,jArray,JNI_FALSE);//把jintArray转换成数组指针
    int i;
    for(i = 0;i<length;i++){
        *(arr_pointer+i) += 10;
        LOGD("数字是 = %d", *(arr_pointer+i));
    }
    (*env)->SetIntArrayRegion(env, jArray, 0, length, arr_pointer);//设置jArray，第三个参数表示起始位置
    (*env)->ReleaseIntArrayElements(env, jArray, arr_pointer, 0);//??
    return jArray;
}

/**
 * 把一个jstring转换成一个c语言的char* 类型.
 */
char *_JString2CStr(JNIEnv *env, jstring jstr) {
    char *rtn = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env, "UTF-8");
    jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
                                                            strencode);
    jsize alen = (*env)->GetArrayLength(env, barr);
    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char *) malloc(alen + 1); //"\0" 这里内存要释放？
        memcpy(rtn, ba, alen);
        rtn[alen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, barr, ba, 0);
    return rtn;
}

//调用工具方法把 java中的string 类型 转换成 C 语言中的 char*
JNIEXPORT jstring JNICALL Java_com_lpan_mine_jnitest_HelloJni_testStringParams
        (JNIEnv *env, jclass jcls, jstring jstr) {
    //
    char *cstr = _JString2CStr(env, jstr);
    //调用strlen 获取 cstr 字符串的长度
    int length = strlen(cstr);
    LOGD("字符串的长度是 = %d",length);
    LOGD("字符串的是 = %s",cstr);
    int i;
    /*for (i = 0; i < length; i++) {
        *(cstr + i) += 1;
    }*/
    return (*env)->NewStringUTF(env, cstr);
}

//使用C代码通过反射的方式 调用java函数
JNIEXPORT jint JNICALL Java_com_lpan_mine_jnitest_HelloJni_testCInvokeJava
        (JNIEnv *env, jobject obj){
    //① 获取java类的字节码，第二个参数是类的全名，其中.使用/替换
    jclass cla = (*env)->FindClass(env,"com/lpan/mine/jnitest/HelloJni");
    //②根据java类的字节码获取java类的函数
    // 第三个参数是方法名. 第四个参数方法描述符，获取方法描述符的cmd命令，javap -s 类名.class
    jmethodID methodID =(*env)->GetMethodID(env,cla,"test","(Ljava/lang/String;)V");
    //③传入java类的对象，java对象的函数，参数调用java函数
    jstring text = (*env)->NewStringUTF(env, "java的方法 在C中被调用");
    (*env)->CallVoidMethod(env,obj,methodID,text);
    return 2;
}


