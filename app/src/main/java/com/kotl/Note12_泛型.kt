package com.kotl


/**
 * Author: L
 * 2022/10/1
 * Description:
 *  1.泛型
 *     01.泛型类. 语法： class  className<T>(var p: T){}
 *     01.泛型函数. 语法：  fun <B> methodName(p: B)
 *  2.协变out
 */

interface Parent {}
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

// 不加out/int 则T既能返回又能做函数参数？
interface ProductionConsumer<T> {
    fun product(): T
    fun consume(item: T)
}

open class Food
open class FastFood : Food()
class Burger : FastFood()
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

class BurgerStore : Production<Burger> {
    override fun product(): Burger {
        return Burger()
    }
}

fun main() {
  var production1: Production<Food> = FoodStore()
    // 因为 Production类添加了out协变，故可以通过编译
  var production2: Production<Food> = FastFoodStore()
  var production3: Production<Food> = BurgerStore()

}