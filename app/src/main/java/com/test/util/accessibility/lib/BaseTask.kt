package com.test.util.accessibility.lib

import android.app.Activity
import android.view.accessibility.AccessibilityNodeInfo
import com.auto.pay.task2.KeyValueEntity

interface BaseTask {
    var curJsonItemIndex: Int

    fun getSearchNode(nodeFinder: NodeFinder): AccessibilityNodeInfo?
    fun getPasswordNode(nodeFinder: NodeFinder): AccessibilityNodeInfo?

    fun getLocalJsonData(): List<KeyValueEntity>
    fun initData()

    fun getJsonList():List<KeyValueEntity>

    fun getAppId():String
    fun getPassword():String
    fun start(aty: Activity, taskEntity: TaskEntity, netWork: Network)
}