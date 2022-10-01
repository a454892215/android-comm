package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */

/**
 * 延迟初始化 lateinit 和 懒加载 by lazy 的使用示例：
 */
class Test5 {
    // 01. 延迟初始化 lateinit ： 变量类型不能是Int等基本类型
    private lateinit var favorite: String

    // 02.  懒加载 by lazy ： 只能使用val 不能使用var
    val config by lazy {
        // 使用的时候调用， 只调用一次， 返回最后一行赋值给目标变量
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