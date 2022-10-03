package com.kotl
// import com.kotl.ut.random
import com.kotl.ut.random as randomizer  // 使用as 别名

/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.扩展函数
 *    01. 不修改原类的情况下，扩展类的功能
 *    02. 语法示例： fun Any.d(str: String){} 与普通函数定义的差别是：函数名前增加了: className.
 *    03.infix 关键字：适用于单个参数的扩展和类函数，使用该关键字修饰的函数，调用时可以省略.和参数括号
 *    04. 如果扩展函数定义在不同包，需要使用import导入
 *    04. import xxx as 别名的使用. 示例： import com.kotl.ut.random as randomizer
 *  2. 泛型扩展函数
 *    01. 示例： fun <T> T.fd(str: String) {}
 *  3. 属性扩展
 *    01: 局限性： Extension property cannot be initialized because it has no backing field
 *    02. 扩展属性中没有field, 不能像正常属性那样赋值
 *  4. 可空类扩展
 *    01：
 *
 */

// 1-1. 扩展String函数 示例
fun String.toMyStr(value: String): String {
    return "$value!!"
}

// 1-2. 扩展Any函数 示例
fun Any.d(str: String) {
    Log.d(str)
}

// 2-1. 扩展泛型函数 示例: 给泛型T的所有对象添加函数fd
fun <T> T.fd(str: String) {
    Log.d(str)
}


// 3-1. 属性扩展: Extension property cannot be initialized because it has no backing field
var String.strLen: Int
    get() = let { // 借用let 获取调用的字符串
        Log.d("let it:$it")
        return it.length
    }
    set(value) {
        //  field = value // 如何正常赋值 ？？？
    }

//4. 可空类扩展
infix fun String?.def(default: String) = println(this ?: default)


class A14 {
    fun <T> getA(t: T) {
        t.fd("调用泛型扩展函数")
        t.let { }
    }
}

fun main() {
    // 使用扩展String的函数
    Log.d("toMyInt:" + "".toMyStr("66"))
    // 使用扩展 Any 的函数
    "".d("=======我是扩展Any的函数====1==")
    // 这种语法太灵活... 相比java ，如果不加限制，容易失控...
    1.d("========我是扩展Any的函数===2==")
    A14().getA("")
    Log.d("我是扩展的属性： " + "11234".strLen)
    var str: String? = null
    str def "bac" //infix修饰的函数def调用示例
    Log.d("可空类扩展验证： " + str?.length)
    val ls: List<Int> = listOf(1, 3, 4, 5, 7)
    // 调用其他类中的扩展函数，如果定义在不同包，需要使用import导入
    Log.d("调用定义在其它文件中的扩展函数示例：" + ls.randomizer())
}