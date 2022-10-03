package com.kotl

/**
 * Author: L
 * 2022/10/1
 * Description:
 * 1. 函数式编程，一个函数式应用通常由三大类函数构成,
 *   01. 变换 transform
 *   02. 过滤 filter :g 过滤函数接受一个predicate函数，返回true添加到集合，false,移除出集合
 *   03. 合并 combine
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
 * 1-2. 过滤函数 filter: 返回新函数，原函数不变
 *   01. filter返回true 表示该元素可以添加到集合 否则从集合移除...
 */
fun testFilter() {
    val ls1 = listOf(2, 4, 6, 8)
    val ls2 = ls1.filter { item -> item != 2 }
    Log.d("===testFilter==ls1: $ls1")
    Log.d("===testFilter==ls2: $ls2")
}


fun main() {
    testMap()
    testFlatMap()
    testFilter()
}