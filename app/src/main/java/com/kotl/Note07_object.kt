package com.kotl



/**
 *  1. object 关键词： 定义只能产生一个实例的类. 声明匿名内部类实例
 *    01.单例对象声明 : a.语法： object 自定义的对象名{}  b.说明：object修饰的对象名，就是单例对象
 *    02.伴生对象：    a.语法： companion object{} 本质上就是一个 类内部的单例对象 用来模拟类的静态方法和静态变量
 *                   b.说明：类中声明的伴生对象属于类，而不属于对象，只会存在一个
 *                   c. companion object 作用域中 可以有多个成员变量和方法
 *
 *    03.对象表达式： 用于临时创建对象，实现接口或继承类，类似 Java 的匿名内部类。
 *       a.语法： val a = object:要需要继承/实现的类名/接口(){}
 *       b.说明：声明一个类使其继承某某类，并返回该类的对象
 *    04.密封类子类单例实例声明： a.语法：  object 自定义类名 : 密封类()
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
        var name = "tome"
        var age = 55

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
    // 伴生对象成员的访问，直接通过类名访问
    Test71.path
    Test71.age
    // 3.调用伴生对象中的成员
    Log.d("object修饰的类可以直接调用成员函数：" + Test71.getPath())
    Log.d("object修饰的类可以直接调用成员函数：" + Test71.getPath())
}