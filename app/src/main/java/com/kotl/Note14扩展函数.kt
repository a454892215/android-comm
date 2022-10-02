package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.扩展函数
 *    01. 不修改原类的情况下，扩展类的功能
 *    02. 语法示例： fun Any.d(str: String){} 与普通函数定义的差别是：函数名前增加了: className.
 *  2. 泛型扩展函数
 *    01.  ...
 */

// 扩展String 示例
fun String.toMyStr(value: String): String {
    return "$value!!"
}

fun Any.d(str: String) {
    Log.d(str)
}


fun main() {
    // 使用扩展String的函数
    Log.d("toMyInt:" + "".toMyStr("66"))
    // 使用扩展 Any 的函数
    "".d("=======我是扩展Any的函数====1==")
    // 这种语法太灵活... 相比java ，如果不加限制，容易失控...
    1.d("========我是扩展Any的函数===2==")
     //.d("========我是扩展Any的函数===3==")
}