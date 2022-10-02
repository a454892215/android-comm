package com.kotl

import java.util.*


/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.vararg 可变参数
 *    01. 语法示例： vararg val p: Int 与Java的...可变参数作用类似
 */

class A13<T>(vararg val p: T)


fun main() {
    val a = A13(1, 2, 3, 0.2, 0.6)
    Log.d("vararg示例:" + a.p.contentToString())
}