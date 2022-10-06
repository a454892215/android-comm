package com.kotl


/**
 * Description:枚举类和 密封类
 *  1.枚举
 *     01. 语法: enum class 类名{}
 *  2.密封类
 *     01. 语法: sealed class 类名{}
 *     02. 说明：a.密封类不能被继承 b.密封类只能在其类的内部或者外部通过object创建全局实例
 *     03. 密封类创建对象语法.  object varName : 密封类()
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