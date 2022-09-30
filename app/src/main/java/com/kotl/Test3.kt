package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */


fun main(args: Array<String>) {
    // 01. 固定列表 List-示例: 不可增删
    val ls: List<String> = listOf("a", "b", "c")
    Log.d("01. 固定列表List-示例: " + ls[0]) //角标越界会抛异常
    Log.d("01. 固定列表List-示例: " + ls.getOrNull(5)) // 不会角标异常，角标越界会返回null
    Log.d("01. 固定列表List-示例: " + ls.getOrElse(5) { "unknown" }) // 不会角标异常，角标越界会返回表达式中返回的值
    // val toMutableList = ls.toMutableList() // 可以转化为可变列表MutableList

    // 02. 可变列表 List-示例： 可以增删
    val ls2: MutableList<String> = mutableListOf("a", "b", "c")
    ls2.add("d")
    ls2.add("d")
    ls2.add("e")
    ls2.removeAt(0)
    Log.d("02. 可变列表mutableList-示例   : $ls2")
    ls2 += "g" // 添加元素
    Log.d("02. 可变列表mutableList-示例 +=: $ls2")
    ls2 -= "d" // 删除元素： 如果存在多个该元素 只删除第一个
    Log.d("02. 可变列表mutableList-示例  -=: $ls2")
    ls2 -= "m" // 删除元素： 如果不存在则不删除
    Log.d("02. 可变列表mutableList-示例  -=: $ls2")
    // ls2.removeIf { it.equals("c") } // 高版本才能兼容


    // 03-1. List迭代方式1
    val ls3: List<String> = listOf("a", "b")
    for (ite in ls3) {
        Log.d("List迭代方式1-for示例  ite: $ite")
    }
    // 03-2. List迭代方式2
    ls3.forEach() {
        Log.d("List迭代方式2-forEach示例  ite: $it")
    }

    // 03-3. List迭代方式3
    ls3.forEachIndexed { index, s -> Log.d("List迭代方式3-forEachIndexed示例  ite: $index $s") }

    // 04. 集合解构
    val ls4: List<String> = listOf("1", "2", "3", "4")
    val (a, _, c) = ls4 // 不需要左右元素个数相同， 从0开始依次匹配, 不需要的元素可以用_命名
    Log.d("04. 集合解构示例 a:$a    c:$c")
}




