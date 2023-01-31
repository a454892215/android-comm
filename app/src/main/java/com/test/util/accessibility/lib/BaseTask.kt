package com.test.util.accessibility.lib

import android.view.accessibility.AccessibilityNodeInfo
import com.auto.pay.task2.KeyValueEntity

interface BaseTask {
    var curJsonItemIndex: Int

    fun getSearchNode(nodeFinder: NodeFinder): AccessibilityNodeInfo?

    fun getLocalJsonData(): List<KeyValueEntity>
    fun initData()

    fun getJsonList():List<KeyValueEntity>
}