package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.泛型
 *     01.泛型类. 语法： class  className<T>(var p: T){}
 *     01.泛型函数. 语法：  fun <B> methodName(p: B)
 */

interface Parent {}
interface Parent2 {
    fun getName(): String
}

// 类上声明的泛型1
data class A12<T>(var p: T)

// 类上声明的泛型2
class A121<T : Parent>(var p: T) {

    // 函数上声明的泛型
    fun <B : Parent2> getAA(p: B): String {
        return p.getName()
    }
}


fun main() {


}