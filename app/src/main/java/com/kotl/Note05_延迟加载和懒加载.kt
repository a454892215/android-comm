package com.kotl



/**
 * 1. 延迟初始化
 *   01: 语法示例 ： private lateinit var favorite: String
 *   02：延迟初始化变量类型不能是Int等基本类型
 *   03：对于lateinit修饰的变量， 如果没有初始化：就使用会抛出：kotlin.UninitializedPropertyAccessException
 * 2.懒加载 by lazy：
 *   01： 语法示例： val config by lazy {1}
 *   02： 变量声明只能使用val 不能使用var
 *   03： 懒加载初始化代码块只会执行一次
 */
class Test5 {
    // 01. 延迟初始化 lateinit ： 变量类型不能是Int等基本类型
    // lateinit 就是 Kotlin 为了让非空类型变量在声明时不必立即赋值，而能延迟到后面某个时刻再初始化的一种方案。
    private lateinit var favorite: String

    // 02.  懒加载 by lazy ： 只能使用val 不能使用var
    val config by lazy {
        // 使用的时候初始化赋值， 只赋值一次
        Log.d("=====懒加载=========")
        1
    }

    // a.  对于lateinit修饰的变量， 如果没有初始化：就使用会抛出：kotlin.UninitializedPropertyAccessException
    fun getMyFavorite1(): String {
        return favorite
    }

    // b. 正常使用示例，使用前保证初始化lateinit修饰的变量
    fun getMyFavorite2(): String {
        //  使用操作符:: 判断属性是否没有被初始化
        if (!::favorite.isInitialized) {
            favorite = "苹果"
        }
        return favorite
    }


}

fun main() {
    val t5 = Test5()
    // Log.d("getMyFavorite1: " + t5.getMyFavorite1()) // 测试 延迟初始化 lateinit
    Log.d("getMyFavorite2: " + t5.getMyFavorite2()) // 测试 延迟初始化 lateinit
    Log.d("config:${t5.config}") // 测试懒加载1
    Log.d("config:${t5.config}") // 测试懒加载2
}