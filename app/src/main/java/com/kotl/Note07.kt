package com.kotl


/**
 * Author: L
 * 2022/10/1
 *  Description:
 */

/**
 * 一 ：object关键词： 定义只能产生一个实例的类. 声明匿名内部类实例
 *    1.对象声明 : a.语法： object 自定义的对象名{}  b.说明：object修饰的变量名，就是单例对象
 *    2.对象表达式： a.语法： val a = object:要需要继承的类名(){}  b.说明：声明一个类使其继承某某类，并返回该类的对象
 *    3.伴生对象： a.语法： companion object{}   b.说明：类中声明的伴生对象属于类，而不属于对象，只会存在一个
 */
// 1. 对象声明
object Test7 {

    fun getName1(): String {
        return "name1"
    }


}

open class Test71 {
    // 3. 伴生对象
    companion object {
        const val path: String = "/a/b"

        init {
            Log.d("========companion object===init===")
        }

        fun getPath(): String {
            return path
        }
    }

}

fun main() {
    // 1.对象声明的使用： 其类名就是其实例对象， 故可以通过Test7直接调用其成员函数
    Log.d("object修饰的类可以直接调用成员函数：" + Test7.getName1())
    // 2.对象表达式 声明一个类使其继承Test71，并返回该类的对象
    val p = object : Test71() {
        fun get(): Int {
            return 1
        }
    }
    // 3.调用伴生对象中的成员
    Log.d("object修饰的类可以直接调用成员函数：" + Test71.getPath())
    Log.d("object修饰的类可以直接调用成员函数：" + Test71.getPath())
}