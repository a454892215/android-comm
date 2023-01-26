package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.os.Build
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.kotl.Log
import com.test.util.utils.AppLog


class MyAccessibilityService : AccessibilityService() {
    companion object {
        var service: MyAccessibilityService? = null

        fun startTask() {
            service?.apply {
                val mNodeInfo = this.rootInActiveWindow
                val list: List<AccessibilityNodeInfo> = mNodeInfo.findAccessibilityNodeInfosByViewId("com.android.settings:id/tv_1")
                for (i in list.indices) {
                    val node: AccessibilityNodeInfo = list[i]
                    var hintText = ""
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        hintText = node.hintText.toString()
                    }
                    AppLog.d("index: $i className: ${node.className}  hintText:${hintText}  text:${node.text}")
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }

            }
            // mNodeInfo.findAccessibilityNodeInfosByText("")
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("======onCreate==============")
        service = this
    }

    override fun onDestroy() {
        Log.d("======onCreate==============")
        service = null
        super.onDestroy()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        Log.d("======onServiceConnected==============")
    }

    // activity start或者bind方式触发，或者设置页面启动辅助服务触发
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
    }

    override fun onInterrupt() {
        AppLog.d(" ====== onInterrupt ========= ")
    }

}
