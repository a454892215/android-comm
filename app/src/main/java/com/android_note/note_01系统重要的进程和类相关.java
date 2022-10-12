package com.android_note;

/**
 * 1. 一个App是怎么启动起来的？
 * 2.  App的程序入口到底是哪里？
 * 3.  Launcher到底是什么神奇的东西？
 * 4.  听说还有个AMS的东西，它是做什么的？
 * 5.  Binder是什么？他是如何进行IPC通信的？

一： 主要对象功能介绍
 * 1. ActivityManagerServices，简称AMS，服务端对象，负责系统中所有Activity的生命周期

 * 2. ActivityThread，App的真正入口。当开启App之后，会调用main()开始运行，开启消息循环队列，这就是传说中的UI线程或者叫主线程。
 *    与ActivityManagerServices配合，一起完成Activity的管理工作
 *
 * 3. ApplicationThread，用来实现 ActivityManagerService 与 ActivityThread 之间的交互。
 *
 * 4. ApplicationThreadProxy 是ApplicationThread代理类，AMS就是通过该代理与ActivityThread进行通信的
 *
 * 5. Instrumentation，每一个应用程序只有一个Instrumentation对象，每个Activity内都有一个对该对象的引用。参与activity声明周期管理
 *
 * 6. ActivityStack，Activity在AMS的栈管理，用来记录已经启动的Activity的先后关系，状态信息等。通过ActivityStack决定是否需要启动新的进程。
 *
 * 7. ActivityRecord，ActivityStack的管理对象，每个Activity在AMS对应一个ActivityRecord，来记录Activity的状态以及其他的管理信息。其实就
 *    是服务器端的Activity对象的映像。
 *
 * 8. TaskRecord，AMS抽象出来的一个“任务”的概念，是记录ActivityRecord的栈，一个“Task”包含若干个ActivityRecord。AMS用TaskRecord确保Activity启动和退出的顺序。
 *    如果你清楚Activity的4种launchMode，那么对这个概念应该不陌生。
 *
 */

/**
 *  二 ：
 * 1. zygote进程： Android系统开启新进程的方式，是通过fork第一个zygote进程实现的。除了第一个zygote进程，其他应用所在的进程都是zygote的子进程
 * 2. SystemServer进程： ActivityManagerService、PackageManagerService、WindowManagerService等进程都是从此进程启动
 * 3. App与AMS通过Binder进行IPC通信，AMS(SystemServer进程)与zygote通过Socket进行IPC通信
 * 4. 在Android系统中，任何一个Activity的启动都是由AMS和应用程序进程主要是ActivityThread）相互配合来完成的
 * 4. 一个App的程序入口:ActivityThread类的main()函数， 任何java进程都是通过main()函数开始启动
 * 5. ActivityThread在main()函数中创建了消息循环，故主线程使用Handler不需要再创建Looper，而如果在其他线程使用线程自己的消息循环需要使用Looper.prepare()和Looper.loop()
 * 6. Application 对象先调用attach()，在调用onCreate()
 *
 */
public class note_01系统重要的进程和类相关 {

}
