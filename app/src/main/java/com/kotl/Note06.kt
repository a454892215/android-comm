package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */

/**
 * 一 ：继承：
 *    a. kotlin类默认是封闭的，要使类可以被继承，需要使用open关键词修饰
 *    b. kotlin的成员函数模式也是封闭的不能被重新，需要使用open修饰才能被重写
 * 二 ：  关键词is 对象类型判断和 as 进行类型转换
 * 三 ： Any是所有类的父类
 */
open class Test6(private var name: String) {

    override fun toString(): String {
        // super.toString()
        return "name:" + this.name
    }

    open fun myName(): String {
        return this.name
    }

}

class Test61(private var name: String) : Test6(name = name) {

    // 1. 重写父类函数 1
    override fun toString(): String {
        return "name1:" + this.name
    }

    //2.  重写父类函数 2
    override fun myName(): String {
        return "name1:" + this.name
    }

    fun print() {
        Log.d("====print=====")
    }
}

fun main() {
    // 3. 智能类型转换 如果去掉变量类型声明Test6, 能直接调用Test61， 和父类Test6的函数
    val t: Test6 = Test61(name = "小小")
    Log.d(t.toString())
    // 4. 使用is 判断对象是否某个类或其子类的是实例
    Log.d("t is Test6:" + (t is Test6))
    Log.d("t is Test61:" + (t is Test61))
    Log.d("t is Any:" + (t is Any))

    // 5. as 类型转换
    (t as Test61).print()
}