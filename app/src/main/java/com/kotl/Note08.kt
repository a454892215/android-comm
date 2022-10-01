package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description: 数据类: class 前使用 data修饰
 */

private data class A(var x: Int, var y: Int) {
    init {
        Log.d("====A====init======")
    }

}

fun main() {
    val a = A(1, 3)
    val b = A(1, 3)
    Log.d("a:$a")
    Log.d("== 比较内容：" + (a == b))
    Log.d("===比较地址：" + (a === b))
    val c = a.copy()
    val d = a.copy(x = 4, y = 8)
    Log.d("c:$c")
    Log.d("d:$d")
    Log.d("== copy后的比较内容：" + (a == c))
    Log.d("== copy后的比较内容：" + (a === c))
    Log.d("== copy后的比较内容：" + (a == d))
}