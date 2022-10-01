package com.kotl


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */


class Man {
    /**
    1. field字段：
    a.field--- 对象的每个属性 都有一个对应的field, set, set。
    b. field不能直接定义,field只在set get作用域能引用
     */
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


/**
 *  1. 主构造函数
 *    a. 语法：紧跟类的()中定义，一般声明成员变量之用
 *    b. 只使用一次的变量使用下划线开头，并且不需要关键字var来声明变量, 此变量在其他成员函数中无法引用
 *    c. 以关键字var声明的变量为成员变量，kotlin，会自动为该变量赋值
 **/
class Anim(_name: String, private var age: Int = 1, private var sex: Int = 1) {
    var name: String = _name // 显示赋值
    // age会被自动赋值，不需要再赋值
    /**
     * 次构造函数：
     *   a.  关键词：constructor
     *   b.  次构造函数需要调用主构造函数
     *   c.  次构造函数不允许使用var来申明成员变量
     *   d.  执行顺序: 主构造函数 -> init块-> 次构造函数块
     */
    constructor(_name: String, _age: Int) : this(_name = _name, age = _age) {
        Log.d("Anim 次构造函数被调用")
    }

    /**
     * 初始化块：
     * a.主构造函数被赋值后调用
     * b.可以在此代码块中对构造函数要赋值的成员变量进行检查
     */
    init {
        Log.d("=====init=======")
        require(age > 0) { "age必须大于0" } // 如果不符合条件 会抛出 IllegalArgumentException
        require(name != "da") { "非法name：$name" }
    }


    override fun toString(): String {
        return this.name + ":" + this.age + ":" + this.sex
    }
}

fun main() {
    val man = Man()
    Log.d(man.name)
    man.name = "abc" // 会调用set
    Log.d(man.name + " money:" + man.money)
    Log.d("(0..10):" + (0..10).shuffled()) // 取[0,10]随机排序
    Log.d("(0..10):" + (0..10).shuffled().first()) // 取[0,10]随机排序的第一个数字

    val anim = Anim(_name = "Tom", _age = 1)

    Log.d("anim: $anim")
}