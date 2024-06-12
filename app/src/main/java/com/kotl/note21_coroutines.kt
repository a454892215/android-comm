package com.kotl

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
class note21 {

}

/**
协程
 */


fun main() {
    sample8()
}

private fun sample0() {
    /**
     * GlobalScope 继承于 CoroutineScope (接口)，
     * 其源码实现是一个全局的单例，因为是单例，其生命周期跟随与整个应用程序的生命周期；可使用 GlobalScope.launch 启动一个顶层协程。
     */

    // ------------应用举例-----------
// GlobalScope 执行任务时，切换不同线程
//
// 1、启动一个协程 (主线程 执行)
    var job1 = GlobalScope.launch(context = Dispatchers.Main) {
        // TODO Main线程
    }

// 2、启动一个协程 (异步线程：线程数量默认为64)
    var job2 = GlobalScope.launch(context = Dispatchers.IO) {
        // TODO 异步线程：线程数量默认为64
    }

// 3、启动一个协程 (异步线程：线程的最大数量等于 CPU 内核数)
    var job3 = GlobalScope.launch(context = Dispatchers.Default) {
        // TODO 异步线程：线程的最大数量等于 CPU 内核数
    }

// 4、启动一个协程 (当前线程 执行)
    var job4 = GlobalScope.launch(context = Dispatchers.Unconfined) {
        // TODO 当前线程执行
    }

// 取消协程
// job.cancel()

    // 协程代码和主现场代码交替执行
    // delay 函数是一个非阻塞式的挂起函数，他只会挂起当前协程，并不会影响到其他协程的运行.
    GlobalScope.launch(context = Dispatchers.IO) {
        for (i in 1..10) {
            println("Coroutine loop $i")
            delay(1000L) // 非阻塞延迟1秒
        }
    }
    // 主线程的阻塞循环
    for (j in 1..5) {
        println("Main thread loop $j")
        Thread.sleep(1000L) // 阻塞延迟1秒
    }
}


private fun sample1() {

    /**
     * runBlocking 函数同样会创建一个协程作用域，它可以保证在协程作用域内所有代码和子协程没有全部执行完之前一直阻塞当前线程.
     * 但需要注意的是，runBlocking 通常只是在测试环境下使用，正式环境使用会产生一些性能上的问题.
     * launch 函数要求必须在协程作用域中才能调用
     * 这种写法主线程和现场不能交替执行
     */
    runBlocking {
        // 启动一个协程
        launch {
            for (i in 1..10) {
                println("Coroutine loop $i")
                delay(1000L) // 非阻塞延迟1秒
            }
        }
    }
    // 主线程的阻塞循环
    for (j in 1..10) {
        println("Main thread loop $j")
        Thread.sleep(1000L) // 阻塞延迟1秒
    }
}

private fun sample2() {

    /**
     * runBlocking 函数同样会创建一个协程作用域，它可以保证在协程作用域内所有代码和子协程没有全部执行完之前一直阻塞当前线程.
     * 但需要注意的是，runBlocking 通常只是在测试环境下使用，正式环境使用会产生一些性能上的问题.
     * 这种写法两个协程代码块交替执行
     */
    runBlocking {
        // 启动一个协程
        launch {
            for (i in 1..10) {
                println("Coroutine loop a $i")
                delay(1000L) // 非阻塞延迟1秒
            }
        }

        launch {
            for (j in 1..5) {
                println("Coroutine loop b $j")
                Thread.sleep(1000L) // 阻塞延迟1秒
            }
        }
    }

}

private fun sample3() {
    var num = 0;
    runBlocking {
        /**
         * 通过 repeat 创建多个协程
         */
        repeat(3) {
            launch {
                num++
                println("Coroutine num $num")
            }
        }
        sample4()
    }

}

/**
 * 随着业务的发展，launch 函数中的代码越来越复杂，需要将部分代码封装到一个函数中，但是这个函数是没有 launch 协程作用域的，怎么使用像 delay() 这样的挂起函数呢？
 *
 * 标记：suspend 只是一个标记，本身并不实现挂起功能，只是用来提醒开发者.  真正实现挂起的是 suspend 修饰的函数中的 delay 函数.
使用范围：suspend 只能再协程体或者其他挂起函数中调用.
 */
suspend fun sample4() {
    println("suspend A 开始处理")
    delay(500)
    println("suspend A 处理完成")
    sample5()
}


/**
 * coroutineScope 函数也是一个挂起函数，因此可以在其他挂起函数中调用.
他的主要特点是会继承外部协程的作用域并创建一个子协程，因此就可以给挂起函数提供协程作用域了，如下
另外 coroutineScope 函数可以保证其作用域内的所有代码和子协程在全部执行完之前，一直挂起外部协程
 */
suspend fun sample5() = coroutineScope {
    launch {
        println("任务 B 开始处理")
        delay(500)
        println("任务 B 处理完成")
    }
}

/**
 * 看上去 coroutineScope 和 runBlocking 函数作用类似，但是 coroutineScope 函数只会阻塞当前协程，不影响其他协程，
 * 也不影响任何线程，因此不会造成性能上损失.  而 runBlocking 会挂起外部线程
 */
fun sample6() {
    runBlocking {
        //   coroutineScope 函数可以保证其作用域内的所有代码和子协程在全部执行完之前，一直挂起外部协程
        //  先执行完成coroutineScope代码 再执行下面协程
        coroutineScope {
            launch {
                for (i in 1..10) {
                    println("Coroutine loop a $i")
                    delay(1000L) // 非阻塞延迟1秒
                }
            }
        }
        launch {
            for (j in 1..5) {
                println("Coroutine loop b $j")
                Thread.sleep(1000L) // 阻塞延迟1秒
            }
        }
        println("Coroutine t name 1 ${Thread.currentThread().name}")
        sample7()
    }
}

private suspend fun sample7() {
    /**
     *  withContext 将当前协程 移至一个I/O线程中执行异步操作
     */
    return withContext(context = Dispatchers.IO) {
        // 返回值为String的Http同步网络请求

        println("Coroutine t name 2 ${Thread.currentThread().name}")
    }
}

/**
 * GlobalScope.async 执行异步网络任务，返回结果更新UI界面。以下举例中，涉及到以下关键词和方法：

async  会启动一个新的协程
await 关键词：，可以使用一个名为 await 的关键词，等待耗时方法返回执行结果。

 */
private fun sample8() {
    // GlobalScope.async 使用
    GlobalScope.launch(Dispatchers.Default) {
        // TODO 执行主线程任务
        println("start 1 : ${Thread.currentThread().name}")
        // 第一个异步网络请求
        async{ // IO thread
            // TODO IO线程 网络请求
            // 返回值为String的Http同步网络请求
            println("async Coroutine 1 t name a ${Thread.currentThread().name}")

        }
        // 第二个异步网络请求
        async(Dispatchers.IO) { // IO thread
            // TODO IO线程 网络请求
            // 返回值为String的Http同步网络请求
            println("async Coroutine 2 t name b ${Thread.currentThread().name}")

        }.await()

        // 待两个结果都返回后
        // val resultData: String = ("${taobaoData.await()}" + baiduData.await())
        // println("resultData:$resultData")
        // 展示UI
        //  textView?.text = resultData           // main thread
    }
    println("aaaa : ${Thread.currentThread().name}")
    // 如果是 android 主线程会一直执行 所以不需要有下面的睡眠
    Thread.sleep(1000L * 5) // 阻塞延迟1秒

}

/**
 * 不建议直接使用 GlobalScope，GlobalScope是一个单例，其生命周期与Android应用生命周期相同，
 * 而且并未与Android生命周期组件(Activity、Service等进行关联)，其声明周期需要研发人员自己管理。
  这里是协程一般使用方式:
 */
class DD {
    lateinit var scope: CoroutineScope
    fun init() {
        // 创建 CoroutineScope （用于管理CoroutineScope中的所有携程）
        scope = CoroutineScope(Job() + Dispatchers.Main)
    }

    fun onDestroy() {
        // 取消该 Scope 管理的所有协程。
        scope.cancel()

    }

    private fun asyncCoroutine() {
        // CoroutineScope 的 launch 方法
        scope.launch(Dispatchers.Main) {
            // TODO 执行主线程任务                 // main thread
            // 第一个异步网络请求
            val taobaoData = async(Dispatchers.IO) { // IO thread
                // TODO IO线程 网络请求
                // 返回值为String的Http同步网络请求
                println("async a Coroutine t name a ${Thread.currentThread().name}")
            }
            // 第二个异步网络请求
            val baiduData = async(Dispatchers.IO) { // IO thread
                // TODO IO线程 网络请求
                // 返回值为String的Http同步网络请求
                println("async b Coroutine t name a ${Thread.currentThread().name}")
            }
          //  taobaoData.await()
            // 待两个结果都返回后
         //   val resultData: String = (taobaoData.await() + baiduData.await())

            // 展示UI
       //     textView?.text = resultData           // main thread
        }
    }

}

