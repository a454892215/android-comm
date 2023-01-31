package com.test.util.accessibility.lib

import android.view.accessibility.AccessibilityNodeInfo
import android.os.SystemClock
import com.test.util.utils.AppLog.d
import com.test.util.utils.AppLog.e


/**
 * 节点加载器...
 */
class NodeFinder {

    companion object {
        const val perSleepTime = 300
    }

    private var tarNode: AccessibilityNodeInfo? = null
    private var id: String? = null
    private var text: String? = null
    private var isWinIdChanged = false
    private var lastPageWinId = 0
    private var tarNodeIndex: Int = -1
    private var root: AccessibilityNodeInfo? = null
    private var maxLoadTimes = (6000.0 / perSleepTime).toInt()
    private var nodeCanNull = false

    private var isAutoMode = true

    fun setIsAutoMode(isAutoMode: Boolean){
        this.isAutoMode = isAutoMode
    }

    fun setMaxExeTimes(maxTimes: Int) {
        maxLoadTimes = maxTimes
    }

    fun getNodeById(id: String?, isWinIdChanged: Boolean = false, tarNodeIndex: Int = -1, nodeCanNull: Boolean = false): AccessibilityNodeInfo? {
        reset()
        this.id = id
        this.tarNodeIndex = tarNodeIndex
        this.nodeCanNull = nodeCanNull
        setup(isWinIdChanged)
        return load()
    }

    private fun setup(isWinIdChanged: Boolean) {
        this.isWinIdChanged = isWinIdChanged
        root = ServiceUtil.getService()!!.rootInActiveWindow
        lastPageWinId = root!!.windowId
    }

    fun getNodeByTag(text: String?, isWinIdChanged: Boolean = false, tarNodeIndex: Int = -1, nodeCanNull: Boolean = false): AccessibilityNodeInfo? {
        reset()
        this.text = text
        this.tarNodeIndex = tarNodeIndex
        this.nodeCanNull = nodeCanNull
        setup(isWinIdChanged)
        return load()
    }

    private fun reset() {
        if (isAutoMode && !Start.net.isRunning) {
            throw StopException("stop")
        }
        id = null
        text = null
        tarNode = null
        tarNodeIndex = -1
    }

    private fun load(): AccessibilityNodeInfo? {
        for (i in 0 until maxLoadTimes) {
            if (isWinIdChanged) {
                if (root!!.windowId != lastPageWinId) {
                    tarNode = findNode()
                }
            } else {
                tarNode = findNode()
            }
            if (tarNode != null) {
                d("找到目标节点成功：id:" + id + " text:" + text + " costTime:" + i * perSleepTime)
                return tarNode
            }
            SystemClock.sleep(perSleepTime.toLong())
        }
        if (!nodeCanNull) {
            e("异常，没有找到目标节点：id:" + id + " text:" + text + " costTime:" + (maxLoadTimes * perSleepTime))
        } else {
            d("寻找结束，没有找到节点：id:" + id + " text:" + text + " costTime:" + (maxLoadTimes * perSleepTime))
        }

        return null
    }

    private fun findNode(): AccessibilityNodeInfo? {
        if (id != null) {
            return getNodeById(id!!, false, tarNodeIndex)
        } else if (text != null) {
            return ServiceUtil.getNodeByTag(text!!, tarNodeIndex)
        } else {
            e("id 和 text不能都是null")
        }
        return null
    }

}