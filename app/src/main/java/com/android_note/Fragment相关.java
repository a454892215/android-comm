package com.android_note;

/**
 * 1. Fragment不推荐通过构造方法直接来传递参数呢，这么做会抛异常：fragment如果被销毁后重新创建的时候系统会调用其默认无参构造函数...
 * 2. Fragment是可以让你的app纵享丝滑的设计，同样的界面Activity占用内存比Fragment要多，响应速度Fragment比Activty在中低端手机上快了很多...
 * 3. getActivity()返回null:Fragment已经onDetach()了宿主Activity
 * 4. 在Fragment基类里设置一个Activity mActivity的全局变量，在onAttach(Activity activity)中赋值。使用mActivity代替
 *     getActivity()，保证Fragment即使在onDetach后，仍持有Activity的引用（有引起内存泄露的风险，但是异步任务没停止的情况下，本身就可能已内存泄漏
 * 5. Can not perform this action after onSaveInstanceState：离开当前Activity等情况下，系统会调用onSaveInstanceState(),该Activity调用onResume()之前
      你执行Fragment事务，就会抛出该异常！
   6. 如果你add()了几个Fragment，使用show()、hide()方法控制，在“内存重启”后回到前台，app的这几个Fragment界面会重叠（v4-24.0.0+修复了此问题）：
      bug版本Fragment的mHidden属性，默认为false， 销毁前没有保存该状态，重启后都是显示的...使用replace不会造成重叠现象
   7. startActivityForResult接收返回问题在support 23.2.0以下的支持库中，对于在嵌套子Fragment的startActivityForResult ()，会发现无论如何都不能在onActivityResult()
      中接收到返回值，只有最顶层的父Fragment才能接收到，这是一个support v4库的一个BUG
 */
public class Fragment相关 {
}
