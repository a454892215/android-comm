package com.kotl


/**
 * Description:
 *  1.泛型
 *     01.泛型类. 语法： class  className<T>(var p: T){}
 *     01.泛型函数. 语法：  fun <B> methodName(p: B)
 *  2.协变 out : 使子类泛型对象可以赋值给父类泛型对象
 *  3.逆变 in : 使父类泛型对象可以赋值给子类泛型对象
 *  4. reified 使泛型编译后不被擦除，使用示例：  inline fun<reified T> getName(){}
 */

interface Parent
interface Parent2 {
    fun getName(): String
}

// 类上声明的泛型1
data class A12<T>(var p: T)

// 类上声明的泛型2
class A121<T : Parent>(var p: T) {

    // 函数上声明的泛型
    fun <B : Parent2> getA(p: B): String {
        return p.getName()
    }

    // reified 使用示例
    inline fun<reified T> getName(){

    }
}

// out 限定了T只能做返回，不能做函数参数
interface Production<out T> {
    fun product(): T
    // fun consume(item: T) // 这里T做函数参数 T会报错
}

// in 限定了T只能做函数参数，不能做返回
interface Consumer<in T> {
    fun consume(item: T)
    // fun product(): T // 这里T做返回 T会报错
}


open class Food
open class FastFood : Food()
class FoodStore : Production<Food> {
    override fun product(): Food {
        return Food()
    }
}

class FastFoodStore : Production<FastFood> {
    override fun product(): FastFood {
        return FastFood()
    }
}

class Boy : Consumer<Food> {
    override fun consume(item: Food) {}
}

class Girl : Consumer<FastFood> {
    override fun consume(item: FastFood) {}
}


fun main() {
    // 1. out 协变示例： 使子类泛型对象可以赋值给父类泛型对象
    var production2: Production<Food> = FastFoodStore()

    // 2. in 逆变示例: 使父类泛型对象可以赋值给子类泛型对象
    var consumer1: Consumer<FastFood> = Boy()

}