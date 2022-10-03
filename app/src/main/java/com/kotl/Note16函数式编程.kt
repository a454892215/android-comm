package com.kotl

/**
 * Author: L
 * 2022/10/1
 * Description:
 * 1. 函数式编程，一个函数式应用通常由三大类函数构成,
 *   01. 变换 transform 遍历接收到的集合，使变换函数作用与集合中的所有元素，返回新的集合
 *   02. 过滤 filter  过滤函数接受一个predicate函数，返回true添加到集合，false,移除出集合
 *   03. 合并 combine  合并函数能将不同集合合并成一个新集合， 这和要求目标集合是集合的集合的flatMap不同
 * 2. 说明：
 *  01. 每类函数都针对集合数据类型设计，目标是产生一个最终结果
 *  02. 函数式编程，用到的函数都是可以组合的， 用多个简单函数来构建复杂计算行为
 */

/**
 * 1-1. 变换函数map: 返回新函数，原函数不变
 *   01. 遍历接收到的集合，使变换函数作用与集合中的所有元素，返回新的集合.
 *   02. map返回集合中的元素个数和输入必须是相同的，但是元素类型可以不同
 */
fun testMap() {
    val ls1 = listOf(2, 4, 6, 8)
    val ls2 = ls1.map { item -> ((item + 1).toString() + "-abc") }
    Log.d("===testMap==ls1: $ls1")
    Log.d("===testMap==ls2: $ls2")
}

/**
 * 1-2. 变换函数flatMap: 返回新函数，原函数不变
 *   01.把多个集合合并成一个集合返回
 *   02.flatMap call could be simplified to flatten()
 */
fun testFlatMap() {
    val ls1 = listOf(listOf(2, 4, 6, 8), listOf(2, 4, 6, 8))
    val ls2 = ls1.flatten() // flatMap call could be simplified to flatten()
    Log.d("===testFlatMap==ls1: $ls1")
    Log.d("===testFlatMap==ls2: $ls2")
}

/**
 * 2-1. 过滤函数 filter: 返回新函数，原函数不变
 *   01. filter返回true 表示该元素可以添加到集合 否则从集合移除...
 */
fun testFilter() {
    val ls1 = listOf(2, 4, 6, 8)
    val ls2 = ls1.filter { item -> item != 2 }
    Log.d("===testFilter==ls1: $ls1")
    Log.d("===testFilter==ls2: $ls2")
}

/**
 * 2-2. 一行代码求素数示例...
 * 01. (2 until 9).map{it}   2到9的集合不包含9
 * 02.  ls.none { it == 0 } 集合中没有符合匹配条件的元素，返回true，否则返回false
 * 03.  ls.any { it == 0 }  集合中存有一个或多个元素符合条件时返回true
 * 04.  ls.all { it == 0 }  当且仅当该集合中所有元素都满足条件时，返回true；否则都返回false
 */
fun test1() {
    val numbers = listOf(2, 10, 12, 14, 15, 17, 19, 22, 23, 27, 28)
    val ret = numbers.filter { number ->
        (2 until number).map { number % it }.none { it == 0 }
    }
    Log.d("素数结果集 :$ret")

}

/**
 * 3-1. 合并函数zip
 *   01. 将不同集合, 相同位置的元素组成一对元素，合并成一个新集合
 *   02. 如果两个集合数目不相同， 按最小size的集合合并
 */
fun testZip() {
    val ls1 = listOf(1, 3, 5)
    val ls2 = listOf(2, 4)
    val ls3 = ls1.zip(ls2) // 返回 [(1, 2), (3, 4)]
    Log.d("===testZip==ls1: $ls1")
    Log.d("===testZip==ls2: $ls2")
    Log.d("===testZip==ls3: $ls3")
}

/**
 * 3-2.  fold函数使用实例
 *   01. 迭代集合中的所有元素， 并且每次迭代可以拿到上次迭代的结果
 *   02. 第一次迭代传入一个初始值，作为上次的迭代结果
 */
fun testFold() {
    val ls1 = listOf(1, 2, 3, 4)
    val ls2 = ls1.fold(0) { lastResult, num ->
        Log.d("accumulator:$lastResult  num: $num")
        lastResult + num
    }
    Log.d("===testFold==ls1: $ls1")
    Log.d("===testFold==ls2: $ls2")
}


fun main() {
    testMap()
    testFlatMap()
    testFilter()
    test1()
    testZip()
    testFold()
}