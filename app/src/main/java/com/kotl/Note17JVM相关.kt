@file:JvmName("MyRxUtil17")
@file:JvmMultifileClass

package com.kotl


/**
 * 1. @JvmName
 *    01. 这个注解的主要用途就是告诉编译器生成的Java类或者方法的名称
 *    02.  例如, Java的泛型编译期擦除，所以 JVM 无法识别签名中泛型的区别, 可以@JvmName命名区别
 * 2. @JvmField
 *    01. 使Kotlin编译器不再对该字段生成getter/setter并将其作为公开字段
 * 3. @JvmOverloads
 *    01. 解决Java不能重载kotlin 有默认参数的方法
 * 4. @JvmStatic
 *    01.  解决在Java中不能直接调用kotlin 中的静态方法
 * 5. @JvmMultifileClass
 *    01. 使用示例，见顶部
 *    02. 和注解@file:JvmName配合使用， 把本文件的函数声明等编译到指定类文件中
 * 6. @JvmSynthetic
 *    02. 有此注解的函数只能给kotlin调用而不能给Java调用

 */

/**
 * 1. @JvmName 示例
 */
@JvmName("fooA")
fun foo(value: List<String>) {
}

@JvmName("fooB")
fun foo(value: List<Int>) {
}

/**
 * 2.  @JvmField 示例
 *     01. 使Kotlin编译器不再对该字段生成getter/setter并将其作为公开字段
 */
class Note17(@JvmField var name: String?, var age: Int)

/**
 * 3.  @JvmOverloads 示例
 *     01. 解决Java不能重载kotlin 有默认参数的方法
 */
class TestKt1 {
    @JvmOverloads
    fun testJvm(a: String, b: Int = 1) {
    }

    fun abc() {
        testJvm("a") // java中不能只传一个参数如此调用此函数，否则报错，除非给该函数加上：@JvmOverloads
        testJvm("a", 3)
    }
}

/**
 * 4.  @JvmStatic 示例
 *     01. 解决在Java中不能直接调用kotlin 中的静态方法
 *     02. kotlin中可以直接调用companion修饰的单例函数:TestKt2.abc1()
 *     03. 而在Java中需要如此调用：  TestKt.Companion.abc1();
 *     04. 如果在Java中也要能像kotlin那样直接调用abc1()，需要给函数加上 @JvmStatic
 */
class TestKt2 {
    companion object {
        @JvmStatic
        fun abc1() {
        }
    }

    fun test() {
        TestKt2.abc1()
    }
}


fun main() {
    Log.d("=========MyRxUtil17===========")
}