package com.kotl.ut
// 在扩展文件中定义扩展函数
fun <T> Iterable<T>.random():T = this.shuffled().first()