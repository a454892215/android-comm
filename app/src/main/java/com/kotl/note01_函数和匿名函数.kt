package com.kotl


/**
 * Author: Pan
 *  Description:
 *  1.变量的声明
 *      01. 语法： val name:String  变量类型可以省略，支持类型的自动推断
 *  2.函数
 *      01. 访问权限默认public 示例 fun getSize(age: Int = 10, sex: Int = 1): Int {return 10}
 *      02. 反括号调用函数，避免java函数名和kotlin关键字冲突，加上反括号， 例如： JTest.`is`()
 *      03. 函数作参数示例： fun test4(a: Int, b: Int, sum: (a: Int, b: Int) -> Int): Int {return sum(a, b)}
 *      04. 函数作参数语法示例： methodName:(a: Int, b: Int) -> Int)  说明：()内申明函数参数， ->后类型表示函数返回类型
 *  3.匿名函数
 *      01. 示例：  val varName: (Int) -> Int = { p -> p } 匿名函数最后一行会自动返回 不需要return
 *      02. 匿名函数可以省略返回类型声明: val test3 = { a: Int, b: Int -> a + b }
 *      03. 匿名函数只有一个参数的时可以使用it代替, it的变量声明可以省略： val test2: (Int) -> Int = {it}
 *      04：调用函数时最后一个参数是函数，可以把函数单独提取出来： test4(2, 8) { a: Int, b: Int -> a + b }
 *      05：函数作为变量传入使用前面要加上符号:: 示例： test4(2, 9, ::methodName)
 */

const val size = 100 // 定义编译时候常量...

// 01.入口函数要定义在类的外面才行, 查看kotlin字节码，ctrl_n 搜索show kotlin, 选择all
fun main(args: Array<String>) {
    // 02.变量的申明， 类型可以省略, kotlin的类型自动推断
    val name = "小花"
    Log.d(name)
    // 03.支持的基本类型：String Char Boolean Int Double List Set Map
    // 04. 不会修改的变量 val
    val age = 18
    Log.d("age:$age")
    // 05. 编译时候常量：
    // 06.range 表达式：
    Log.d(" 18在 9到25之间吗？" + (18 in 9..25))
    Log.d(" 18不在 9到25之间吗？" + (18 !in 9..25))
    //07. when
    when (2) {
        1 -> {
            Log.d("week is 2")
        }
        2 -> {}
        3 -> {}
        else -> {}
    }
    // 0 Java的void kotlin使用Unit代替
    // 08. 字符串模板
    Log.d("age： $age  ${if (age < 20) "年轻" else "老了"}")

    //09. a.函数定义 ，参数可以定义默认参数， 默认public, 括号参数后面是返回值定义
    fun getSize(age: Int = 10, sex: Int = 1): Int {
        if (sex == 1) {
            return age
        }
        return 11
    }
    Log.d("函数定义示例：" + getSize(9))
    // b.Unit 为默认返回类型可以不写，表示无返回
    fun getSize2() {}
    Log.d("Unit函数定义示例：" + getSize2())
    // c. Nothing 返回类型
    fun getSize3() {
        // TODO("终止执行...")
    }
    getSize3()
    //10. 反括号调用函数，避免java函数名和kotlin关键字冲突，加上反括号
    JTest.`is`()
    //11.匿名函数： 匿名函数最后一行会自动返回 不需要return
    val c1 = "account".count()
    val c2 = "account".count { letter -> letter == 'c' } // 定制count函数
    Log.d("c1:$c1")
    Log.d("c2:$c2")
    // 12.匿名函数： 申明函数类型变量： 声明一个函数变量，函数返回值为int, 注意：匿名函数变量的参数类型申明和变量声明位置不同
    val test: (Int) -> Int = { p ->
        p // 返回p
    }
    Log.d("12.调用函数变量：" + test(9))
    //13.匿名函数：匿名函数只有一个参数的时候可以使用关键字it代替, it的变量声明也可以省略：
    val test2: (Int) -> Int = {
        it // 返回it
    }
    Log.d("13.匿名函数it关键字示例：" + test2(9))
    //14. 匿名函数可以省略返回类型声明:
    val test3 = { a: Int, b: Int -> a + b }
    Log.d("14.匿名函数省略函数返回声明示例：" + test3(2, 3))

    //15. 函数参数
    fun test4(a: Int, b: Int, sum: (a: Int, b: Int) -> Int): Int {
        return sum(a, b)
    }
    // 如果最后一个参数是函数，则可以把函数单独提取出来
    Log.d("15.函数参申明和调用数示例1：" + test4(2, 8) { a: Int, b: Int -> a + b })
    fun test4_1(a: Int, b: Int): Int {
        return a + b
    }
    Log.d("15.函数参申明和调用数示例2：" + test4(2, 9, ::test4_1))

    //16. 函数返回类型定义:第一个->指向的类型是sum函数的返回类型，第二个->指向的Int表示返回函数类型
    fun test5(a: Int, b: Int, sum: (a: Int, b: Int) -> Int): (a: Int, b: Int) -> Int {
        fun a(c: Int, d: Int): Int {
            val s = sum(a, b)
            return a + b + c + d + s
        }
        return ::a
    }

}




