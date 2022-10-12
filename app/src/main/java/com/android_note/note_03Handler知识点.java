package com.android_note;

/**
 * 01.Handler Looper Message 关系是什么:
 *     Can't create handler inside thread that has not called Looper.prepare()
 *         01. 创建与线程绑定的Looper，同时会创建一个与之关联的 MessageQueue用于存放消息
 *         02. 开启消息循环，从MessageQueue中获取待处理消息，若无消息会阻塞线程（Linux系统的epoll机制）
 *         03. 通过Handler发送消息，此时会将Message入队列到MessageQueue中，并且唤醒等待的Looper
 *         04. Looper获取的消息会投递给对应的Handler处理
 * 02. Android只有在主线程中才能更新UI，所以主线程又叫UI线程，不能在UI线程做耗时操作，会卡屏幕，甚至黑屏，
 * 03. 回到主线程方法：1. View.post 2.activity的runOnUIThread(Runnable) 3.主线程创建的handler 4. AsyncTask
 * 04. Looper.getMainLooper()-通过该方法可以在任意地方获取到主线程的Looper；
 * 05. Looper.quit() Looper.quitSafely()-退出Looper，自主创建的Looper建议在不使用的时候退出
 * 06. MessageQueue会根据post delay的时间排序放入到链表中，链表头的时间小，尾部时间最大。
 * 07. handler.postDelay也是直接存入MessageQueue,以MessageQueue的时间顺序排列和定时唤醒保证精确执行
 */
public class note_03Handler知识点 {
}
