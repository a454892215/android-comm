package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */


fun main(args: Array<String>) {
    // 01. 固定列表 List-示例: 不可增删
    val ls: List<String> = listOf("a", "b", "c", "c")
    Log.d("01-1. 固定列表List-取值示例: " + ls[0]) //角标越界会抛异常
    Log.d("01-2. 固定列表List-取值示例: " + ls.getOrNull(5)) // 不会角标异常，角标越界会返回null
    Log.d("01-3. 固定列表List-取值示例: " + ls.getOrElse(5) { "unknown" }) // 不会角标异常，角标越界会返回表达式中返回的值
    Log.d("01-4. 固定列表List-取值示例: $ls") // 不会角标异常，角标越界会返回表达式中返回的值
    Log.d("01-5. 固定列表List-去重示例: " + ls.distinct())
    // val toMutableList = ls.toMutableList() // 可以转化为可变列表MutableList

    // 02. 可变列表 MutableList-示例： 可以增删
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


    // 05. Set集合
    val set1: Set<String> = setOf("1", "2", "3", "4")
    val set2: MutableSet<String> = mutableSetOf("1", "2", "3", "4")
    Log.d("05-1. 固定Set集合示例 set1:$set1")
    Log.d("05-2. 可变Set集合示例 set2:$set2")
    Log.d("05-3. 可变Set集合取值示例 el:${set2.elementAt(0)}")
    val setToList: List<String> = set1.toList()
    Log.d("05-4. Set集合转List示例 setToList:$setToList")

    //06. 数组
    val arr = intArrayOf(1, 2, 4)
    Log.d("06-1. intArrayOf() 创建数组示例 :${arr.toList()}")

    //07.Map : a.使用to添加键值对  b.使用Pair添加键值对
    val map: MutableMap<String, Int> = mutableMapOf("a" to 2, "b" to 3, Pair("c", 4))
    map["d"] = 5 // 添加元素
    Log.d("07-1. mutableMapOf() 创建数map例 :${map.toList()}")
    Log.d("07-2. map 读取元素 :${map["a"]}")
    Log.d("07-3. map 读取元素 :${map["w"]}") // 键值不存在，则返回null

}




