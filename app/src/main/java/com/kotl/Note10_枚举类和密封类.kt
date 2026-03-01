package com.kotl


/**
 * Description:枚举类和 密封类
 *  1.枚举
 *     01. 语法: enum class 类名{}
 *  2.密封类
 *      作用： 在编译的时候确认类的直接子类的数目是确定的，不会动态增加或者减少的
 *      语法特征： “直接子类”必须和密封类定义在同一个 Kotlin 源文件
 *     01. 语法: sealed class 类名{}
 *     02. 说明：密封类本身不能被直接实例化，只能通过其子类创建实例
 *     03. 直接子类声明语法.  object/class 对象名 : 密封类()
 */

// 1-1: 枚举类
private enum class A10(private var size: Int) {
    Left(2), // 调用构造函数， 声明枚举实例
    Right(3),
    Back(1);

    fun getSize(): Int {
        return this.size
    }
}

private object A3 : B10("1") // 2-2在密封类的外包创建密封类是全局实例

// 2-1: 密封类
private sealed class B10(var a: String = "") {
    object A1 : B10("1") // 创建一个B10匿名子类的对象，对象名字为A1
    private object A2 : B10("2") // 创建一个B10匿名子类的对象，对象名字为A2

    fun getA1(): B10 = A2 // 返回A1对象
}


fun main() {
    Log.d("调用枚举类的属性a：" + A10.Left.getSize())
    Log.d("调用枚举类的属性a：" + A10.Right.getSize())
    Log.d("调用枚举类的属性a：" + A10.Back.getSize())
    Log.d("调用密封类的属性a：" + B10.A1.a)

}