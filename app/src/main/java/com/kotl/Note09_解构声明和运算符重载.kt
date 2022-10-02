package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:解构声明 和运算符重载：
 *  1.解构声明
 *     01. 语法: operator fun component|Index|() = 属性名
 *     02. 对于数据类，定义在构造函数中的成员，会自动生成其解构声明
 *  2. 运算符重载
 *     01. 语法: operator fun 运算符对应的函数名(参数) = 表达式
 */

private class A09(var x: Int, var y: Int) {
    // 1-a. 解构声明的序号从1开始依次增加
    operator fun component1() = x
    operator fun component2() = y

    // 2-a. 重载+号
    operator fun plus(other: A09) = A09(x + other.x, y + other.y)
}

fun main() {
    // 1-b. 解构申明的使用，对象的返回是根据解构声明返回的
    val (x, y) = A09(4, 6)
    Log.d("x:$x  y:$y")

    // 2.b 重写+号运算符验证
    val (x1, y1) = A09(3, 7) + A09(17, 3)
    Log.d("重写+号运算符验证:x1:$x1  y1:$y1")
}