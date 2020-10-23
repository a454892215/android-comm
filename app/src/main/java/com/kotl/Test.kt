package com.kotl

import java.util.*

/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */

class Mime {

    // 即isEmpty这个属性，是判断该类的size属性是否等于0
    var name: String = ""
        get() = "随意怎么修改都不会改变"


    var str2 = ""
}

// 测试
fun main(args: Array<String>) {
    val mime = Mime()
    println("name = ${mime.name}")
    mime.str2 = "你好"
    println("str2 = ${mime.str2}")
}

