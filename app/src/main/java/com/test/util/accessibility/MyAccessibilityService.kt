package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.kotl.Log
import com.test.util.utils.AppLog


class MyAccessibilityService : AccessibilityService() {
    companion object {
        var service: MyAccessibilityService? = null

        /**
         * @id 格式如： "com.test.product_2:id/tv_1"
         */
        fun performClick(id: String) {
            service?.apply {
                val mNodeInfo = this.rootInActiveWindow
                val list: List<AccessibilityNodeInfo> = mNodeInfo.findAccessibilityNodeInfosByViewId(id)
                if (list.isEmpty() || list.size > 1) {
                    AppLog.e("===== size ${list.size}")
                }
                for (i in list.indices) {
                    val node: AccessibilityNodeInfo = list[i]
                    AppLog.d("index: $i className: ${node.className}   text:${node.text}  ")
                    node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }
            }
        }

        fun performSetTextToInput(id: String, text: String) {
            service?.apply {
                val mNodeInfo = this.rootInActiveWindow
                val list: List<AccessibilityNodeInfo> = mNodeInfo.findAccessibilityNodeInfosByViewId(id)
                if (list.isEmpty() || list.size > 1) {
                    AppLog.e("===== size ${list.size}")
                }
                for (i in list.indices) {
                    val node: AccessibilityNodeInfo = list[i]
                    //var hintText = getHintText(node)
                    // AppLog.d("index: $i className: ${node.className}   text:${node.text}  hintText:${hintText}")
                    val arguments = Bundle()
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
                    node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
                }
            }
        }

        private fun getHintText(node: AccessibilityNodeInfo): String {
            var hintText = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if (node.hintText != null) {
                    hintText = node.hintText.toString()
                }
            }
            return hintText
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("======onCreate==============")
        service = this
        val myIntent = Intent(this, AccessibilityActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(myIntent)
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
