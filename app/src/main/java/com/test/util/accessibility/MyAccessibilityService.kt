package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.test.util.utils.AppLog


class MyAccessibilityService : AccessibilityService() {


    // activity start或者bind方式触发，或者设置页面启动辅助服务触发
    override fun onServiceConnected() {
        super.onServiceConnected()
        AppLog.d("======onServiceConnected=========")
        val mNodeInfo = this.rootInActiveWindow
        val list: List<AccessibilityNodeInfo> = mNodeInfo.findAccessibilityNodeInfosByViewId("com.android.settings:id/force_stop_button")
        for (i in list.indices) {
            val node: AccessibilityNodeInfo = list[i]
            node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        }
       // mNodeInfo.findAccessibilityNodeInfosByText("")
    }


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        AppLog.d("======onAccessibilityEvent======${event.toString()}")
    }

    override fun onInterrupt() {
        AppLog.d(" ====== onInterrupt ========= ")
    }

}