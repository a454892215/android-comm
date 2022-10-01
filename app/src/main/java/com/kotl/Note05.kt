package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */

/**
 * 延迟初始化 lateinit 的使用示例：
 */
class Test5 {
    // 01. 延迟初始化： 变量类型不能是Int等基本类型
    private lateinit var favorite: String


    // 02. 不能命名getFavorite， 会和默认函数冲突
    // 03. 如果没有初始化：就使用会抛出：kotlin.UninitializedPropertyAccessException
    fun getMyFavorite1(): String {
        return favorite
    }

    // 03. 正常使用示例，使用前保证初始化
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
    // Log.d("getMyFavorite1: " + t5.getMyFavorite1())
    Log.d("getMyFavorite2: " + t5.getMyFavorite2())

}