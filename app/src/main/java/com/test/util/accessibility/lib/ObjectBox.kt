package com.test.util.accessibility.lib

import android.content.Context
import com.test.util.box_obj.MyObjectBox

import io.objectbox.BoxStore

// 单例
object ObjectBox {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }
}