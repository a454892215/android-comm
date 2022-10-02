package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:枚举类和 密封类
 *  1.枚举
 *     01. 语法: enum class 类名{}
 *  1.密封类
 *     01. 语法: sealed class 类名{}
 */

// 1-1: 枚举类
private enum class A10(private var size: Int) {
    Left(2), // 调用构造函数， 声明枚举实例
    Right(3),
    Back(1);

    fun getSize(): Int {
        return this.size
    }
}

// 1-1: 枚举类密封类
private sealed class B10 {
    private object B1 : B10()

    var a: String = ""
    var b: String = ""

    fun getB1(): B10 = B1
}

fun main() {
    Log.d(A10.Left.getSize())
    Log.d(A10.Right.getSize())
    Log.d(A10.Back.getSize())
}