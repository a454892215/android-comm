package com.kotl

/**
 * Author: L
 * 2022/10/1
 * Description:
 * 1. 泛型扩展函数函特征：
 *    01. 函数中不能return
 *    02. 函数中传入this,
 *    03. 函数中传入it
 *    04. kotlin的函数参数实际上是申明了一个函数，定义了该函数的参数，返回等语法特征
 */


/**
 *  1. block: () -> T
 *    01.传入的函数无参
 *    02.该函数最后一行需要是调用者对象类型，而且无return
 */
fun <T> T.method1(block: () -> T): T {
    return block()
}

/**
 *  2. block: (T) -> T
 *    01.传入的函数带有自身作为参数
 *    02.该函数最后一行需要是调用者对象类型，而且无return
 *    03.把调用者作为it,传入定义的lambda表达式函数域中
 */
fun <T> T.method2(block: (T) -> T): T {
    return block(this)
}

/**
 * 3. block: T.() -> T
 *   01.传入的函数无参
 *   02.该函数最后一行需要是调用者对象类型，而且无return
 *   03.把调用者作为this,传入定义的lambda表达式函数域中
 */
fun <T> T.method3(block: T.() -> T): T {
    Log.d("=======1==this:$this")
    return block()
}

fun main() {

    "====".method1 {
        Log.d("======2===this:")
        "";
        // 03. 不能带有return
    }

    "====".method2 {
        Log.d("======2===this:$it")
        it
        // 03. 不能带有return
    }

    "====".method3 {
        Log.d("======2===this:$this")
        this
        // 0.3 不能带有return
    }
}