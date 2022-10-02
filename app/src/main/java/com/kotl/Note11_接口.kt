package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.接口
 *     01. 语法 interface 接口名
 *     02. 接口中的函数默认open, 不需要再加open
 *     03. 实现接口的所有函数和属性都需要添加override关键词
 *  2.
 *     01.
 */

interface A11{
    var name :String // 定义接口属性
    fun getC() // 定义接口函数
}

class B11: A11{
    override var name: String = ""

    override fun getC() {
        TODO("Not yet implemented")
    }

}

fun main() {


}