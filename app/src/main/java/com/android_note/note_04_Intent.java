package com.android_note;

/**
 * 跨进程通信：
 * 1. Binder性能ok，用起来也方便，但是有大小的限制，传的数据量大了就会抛异常。
 * 2. Socket或者管道性能不太好，涉及到至少两次拷贝
 * 3. 共享内存性能还不错，可以考虑，关键看怎么实现
 * 4. binder调用的缓冲buffer大小当前为1M ,由当前进程共享。因此，如果同时有多个调用，就算单个调用过程传输的数据不大，
 *    也有可能触发此异常。可以将数据分成多个小数据来避免此异常
 * 5. Intent普通传大图方式为啥会抛异常而putBinder为啥可以？是因为Intent启动组件时，系统禁掉了文件描述符fd,bitmap无法利用共享内存，
 *    只能采用拷贝到缓冲区的方式，导致缓冲区超限,触发异常;putBinder的方式，避免了intent 禁用描述符的影响，bitmap 写parcel时的fd 默认是true,可以利用到共享内存
 */
public class note_04_Intent {

    public static void main(String[] args) {

    }
}
