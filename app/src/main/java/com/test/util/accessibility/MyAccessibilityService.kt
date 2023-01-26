package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.common.comm.L
import com.common.utils.ToastUtil
import com.kotl.Log
import com.test.util.utils.AppLog


class MyAccessibilityService : AccessibilityService() {
    companion object {
        var service: MyAccessibilityService? = null

        /**
         * @id 格式如： "com.test.product_2:id/tv_1"
         */
        fun performClickById(id: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performClick(list)
            }
        }

        fun performClickByTag(tag: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
                performClick(list)
            }
        }

        private fun performClick(list: List<AccessibilityNodeInfo>) {
            if (list.isEmpty() || list.size > 1) AppLog.e("===== size ${list.size}")
            for (i in list.indices) {
                val node: AccessibilityNodeInfo = list[i]
                AppLog.d("index: $i className: ${node.className}   text:${node.text}  ")
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }


        fun performSetTextToInputById(id: String, text: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performSetTextToInput(list, text)
            }
        }

        fun performSetTextToInputByTag(tag: String, text: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
                performSetTextToInput(list, text)
            }
        }

        private fun performSetTextToInput(list: List<AccessibilityNodeInfo>, text: String) {
            if (list.isEmpty() || list.size > 1) AppLog.e("===== size ${list.size}")
            for (i in list.indices) {
                val node: AccessibilityNodeInfo = list[i]
                val arguments = Bundle()
                val hintText = getHintText(node)
                AppLog.d("text: ${node.text} hintText:$hintText")
                arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
                node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
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


        fun exeGesture(x: Float = L.dp_1 * 180, y: Float = L.dp_1 * 360) {
            service?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val builder = GestureDescription.Builder()
                    val path = Path()
                    path.moveTo(x, y)
                    builder.addStroke(GestureDescription.StrokeDescription(path, (400L..600L).random(), (400L..600L).random()))
                    val gestureDescription: GestureDescription = builder.build()
                    this.dispatchGesture(gestureDescription, object : GestureResultCallback() {
                        override fun onCompleted(gestureDescription: GestureDescription?) {
                            AppLog.d(" exeGesture ===== onCompleted ===== ")
                        }

                        override fun onCancelled(gestureDescription: GestureDescription?) {
                            AppLog.d(" exeGesture ==== onCancelled ===== ")
                        }
                    }, null)
                } else {
                    ToastUtil.showLong("手机最低版本号必须大于24")
                    AppLog.e(" ==== 手机最低版本号必须大于24 ===== ")
                }


            }
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
