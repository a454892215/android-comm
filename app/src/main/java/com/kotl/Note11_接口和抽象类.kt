package com.kotl


/**
 *  1.接口
 *     01. 语法 interface 接口名{}
 *     02. 接口中的函数默认open, 不需要再加open
 *     03. 实现接口的所有函数和属性都需要添加override关键词
 *  2.抽象类
 *     01. 语法： abstract class 类名{}
 *     02. 和Java一样不支持多继承, 但是可以实现多个接口
 */

interface A11 {
    var name: String // 定义接口属性
    fun getC() // 定义接口函数
}

interface A111 {
}

class B11 : A11 {
    override var name: String = ""

    override fun getC() {
        TODO("Not yet implemented")
    }

}

// 抽象类
abstract class C111 {}

// 抽象类：实现了A11， A111两个接口和继承了一个类：C111
abstract class C11 : A11, A111, C111() {
}

fun main() {


}