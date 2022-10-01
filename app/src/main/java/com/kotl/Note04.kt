package com.kotl



/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */


class Man {
    // 1. 对象的每个属性 都有一个对应的field, set, set。
    // 2. field不能直接定义,field只在set get作用域能引用
    var name: String = "Tom"
        get() = field.trim()
        set(value) { // 初始化不会调用set
            Log.d("===set=name 被调用====$value")
            field = value.trim()
        }
    val money: Int
        get() {
            return (0..10).shuffled().first()
        }
}

fun main() {
    val man = Man()
    Log.d(man.name)
    man.name = "abc" // 会调用set
    Log.d(man.name + " money:" + man.money)
    Log.d("(0..10):" + (0..10).shuffled()) // 取[0,10]随机排序
    Log.d("(0..10):" + (0..10).shuffled().first()) // 取[0,10]随机排序的第一个数字
}