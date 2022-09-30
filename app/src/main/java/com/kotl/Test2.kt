package com.kotl

import java.io.File
import kotlin.math.roundToInt


/**
 * Author: Pan
 * 2020/10/17
 *  Description:
 */


// 01.入口函数要定义在类的外面才行, 查看kotlin字节码，ctrl_n 搜索show kotlin, 选择all
fun main(args: Array<String>) {
    // 01. 可空控制符
    var a: String? = "bac"
    a = null
    Log.d("a调用不会发生空指针：" + a?.length) //  ? 不强行调用， 不会发生空指针异常
    /// 02.强制非空符
    // Log.d(a!!.count()) //，可能会引起空指针异常
    // 03. 空合并操作符 ?:
    Log.d("空合并操作符：" + (a ?: "2")) // a为null,返回右边，否则返回a
    // 04. substring
    val str = "ni,hao,gyz"
    Log.d("substring 示例：" + str.substring(0 until 2))
    // 05. split
    Log.d("split示例：" + str.split(","))
    // 05. replace
    val newStr = str.replace(Regex("[gyz]")) {
        when (it.value) {
            "g" -> "1"
            "y" -> "2"
            "z" -> "3"
            else -> it.value
        }
    }
    Log.d("自定义规则replace示例：$newStr")
    //06. ==和===
    val str1 = "abc"
    val str2 = "abc"
    Log.d("==的比较示例：" + (str1 == str2)) // ==比较字符是否相等 true
    Log.d("===的比较示例" + (str1 === str2)) // 比较内存地址是否相等 true
    Log.d("id1:" + str1.hashCode() + " id2:" + str2.hashCode())

    //07. forEach遍历
    "abcdef".forEach {
        Log.d("forEach it:$it")
    }

    // 08. 数字类型转换
    Log.d("数字类型转换示例1: " + ("8.12".toIntOrNull())) // null
    Log.d("数字类型转换示例2: " + ("8.12".toDoubleOrNull())) // 8.12
    Log.d("toInt数字类型转换示例3: " + (8.523.toInt())) // 舍弃小数位转换
    Log.d("roundToInt数字类型转换示例4: " + (8.523.roundToInt())) // 四舍五入转换

    // 09. apply 示例， 会返回调用者对象
    var f: File = File("/test.txt").apply {
        setWritable(true)
        setReadable(true)
    }

    // 10. let示例， 调用者作为it传入作用域，返回作用域最后一行
    val ret = listOf(2, 3, 4).first().let {
        it * it
    }
    fun getHint(name: String?): String {
        return name?.let { "welcome $it."} ?: "what your name?"
    }
    Log.d("10. let示例1： " + getHint("Sand"))
    Log.d("10. let示例2： $ret")
}




