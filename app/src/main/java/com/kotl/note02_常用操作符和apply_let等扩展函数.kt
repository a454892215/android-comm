package com.kotl

import java.io.File
import kotlin.math.roundToInt


/**
 *  1.常见操作符
 *  01. 申请可空变量 val a: String? = null
 *  02. null也强制调用 a!!.count()
 *  03. 合并操作符  a ?: "2"   a为null,返回右边，否则返回a
 *  2.扩展函数
 *  01. apply 示例: a.apply {)   a调用者作为this传入作用域，b.返回调用者对象
 *  02. run   示例: a.run {) ，   a.调用者作为this传入作用域，b.返回作用域最后一行
 *  03  also  示例: a.also {}   a.调用者作为it传入作用域，b.返回调用者对象
 *  04. let   示例: a.let {)     a.调用者作为it传入作用域，b.返回作用域最后一行
 *  05  takeIf  示例: ...   a.调用者作为it传入作用域，b.作用域最后一行为true返回调用者对象，false返回null
 *  06  takeUnless  示例:...  a.调用者作为it传入作用域，b.作用域最后一行为false返回调用者对象，true返回null
   补充的普通函数 with
 *  with  示例: val isLong = with(""){} ， a.接受一个参数作为this传入作用域 ，b.返回作用域最后一行

 */


// 01.入口函数要定义在类的外面才行, 查看kotlin字节码，ctrl_n 搜索show kotlin, 选择all
fun main(args: Array<String>) {
    // 01. 可空控制符
    val a: String? = null
    Log.d("01. a调用不会发生空指针：" + a?.length) //  ? 不强行调用， 不会发生空指针异常
    /// 02. 强制非空符
    // Log.d(a!!.count()) //，可能会引起空指针异常
    // 03. 空合并操作符 ?:
    Log.d("03. 空合并操作符：" + (a ?: "2")) // a为null,返回右边，否则返回a
    // 04. substring
    val str = "ni,hao,gyz"
    Log.d("04. substring 示例：" + str.substring(0 until 2))
    // 05. split
    Log.d("05. split示例：" + str.split(","))
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
    Log.d("06. ==的比较示例：" + (str1 == str2)) // ==比较字符是否相等 true
    Log.d("06. ===的比较示例" + (str1 === str2)) // 比较内存地址是否相等 true
    Log.d("id1:" + str1.hashCode() + " id2:" + str2.hashCode())

    //07. forEach遍历, it作为迭代到的元素传入作用域
    "abcdef".forEach {
        Log.d("07. forEach it:$it")
    }

    // 08. 数字类型转换
    Log.d("08. 数字类型转换示例1: " + ("8.12".toIntOrNull())) // null
    Log.d("08. 数字类型转换示例2: " + ("8.12".toDoubleOrNull())) // 8.12
    Log.d("08. toInt数字类型转换示例3: " + (8.523.toInt())) // 舍弃小数位转换
    Log.d("08. roundToInt数字类型转换示例4: " + (8.523.roundToInt())) // 四舍五入转换

    // 09. apply 示例， a.调用者作为this传入作用域，b.返回调用者对象
    val f: File = File("/test.txt").apply {
        this.setWritable(true)
        this.setReadable(true)
    }
    Log.d("09. apply-示例：" + f.absolutePath)

    // 10. let示例， a.调用者作为it传入作用域，b.返回作用域最后一行
    val ret = listOf(2, 3, 4).first().let {
        it * it
    }

    fun getHint(name: String?): String {
        return name?.let { "welcome $it." } ?: "what your name?"
    }
    Log.d("10. let-示例1： $ret")
    Log.d("10. let-示例2： " + getHint("Sand"))


    // 11. run 示例， a.调用者作为this传入作用域，b.返回作用域最后一行
    val f2: File = File("/test.txt").run {
        this.setWritable(true)
        this.setReadable(true)
        this
    }
    Log.d("11. run-示例：" + f2.absolutePath)

    // 12. with 示例， a.接受一个参数作为this传入作用域 ，b.返回作用域最后一行
    val isLong = with("123456"){
        this.length > 5
    }
    Log.d("12. with-示例：$isLong")

    // 13. also 示例，  a.调用者作为it传入作用域，b.返回调用者对象
    val f3: File = File("/test.txt").also {
        it.setWritable(true)
    }
    Log.d("13. also-示例：" + f3.absolutePath)

    // 14. takeIf 示例，  a.调用者作为it传入作用域，b.作用域最后一行为true返回调用者对象，false返回null
    val f4: File? = File("test.txt").takeIf {
        it.setWritable(true)
        false
    }
    Log.d("14. takeIf-示例：${f4?.absolutePath}")

    // 15. takeUnless 示例，  a.调用者作为it传入作用域，b.作用域最后一行为false返回调用者对象，true返回null
    val f5: File? = File("test.txt").takeUnless {
        it.setWritable(true)
        false
    }
    Log.d("15. takeUnless-示例：${f5?.absolutePath}")
}




