package com.test.util.accessibility

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.Bundle
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import com.common.comm.L
import com.common.utils.ToastUtil
import com.test.util.base.MyBaseActivity
import com.test.util.utils.AppLog

@SuppressWarnings("unused")
class AccessibilityUtil {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var service: AccessibilityService? = null

        fun init(obj: AccessibilityService) {
            service = obj
        }

        fun destroy() {
            service = null
        }

        /**
         * 递归获取所有子Node 包含NodeParent,
         */
        @SuppressWarnings("unused")
        fun getAllNodeFromRoot(node: AccessibilityNodeInfo?): List<AccessibilityNodeInfo> {
            val list: MutableList<AccessibilityNodeInfo> = ArrayList()
            node?.apply {
                if (node.childCount > 0) {
                    for (i in 0 until node.childCount) {
                        val v = node.getChild(i)
                        list.add(v)
                        list.addAll(getAllNodeFromRoot(v))
                    }
                }
            }
            return list
        }

        /**
         * 通过hintText查找节点设置文本，要求Android 8.0版本以上
         */
        fun performSetTextToInputByHintText(hintText: String, text: String) {
            val root: AccessibilityNodeInfo? = service?.rootInActiveWindow
            val list: List<AccessibilityNodeInfo> = getAllNodeFromRoot(root)
            //  AppLog.d("performSetTextToInputByHintText size: ${list.size}")
            for (node in list) {
                //有一种办法可以避开Android 8.0版本的限制，即先以坐标方式定位到EditText,点击获取焦点，再根据焦点获取该node
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (node.hintText != null && node.hintText.toString() == hintText) {
                        performSetTextToNodes(listOf(node), text)
                        // AppLog.d(" text: ${node.text}   hintText: ${node.hintText}")
                        break

                    }
                } else {
                    AppLog.e("需要版本号大于等于26.")
                }
            }
        }

        /**
         * 给获取焦点的Input设置文本
         */
        fun performSetTextToInputForFocusedNode(text: String) {
            val root: AccessibilityNodeInfo? = service?.rootInActiveWindow
            val list: List<AccessibilityNodeInfo> = getAllNodeFromRoot(root)
            for (node in list) {
                if (node.isFocused) {
                    performSetTextToNodes(listOf(node), text)
                    break
                }
            }
        }

        /**
         * @id 格式如： "com.test.product_2:id/tv_1"
         * 通过ID查找节点，点击该节点
         */
        fun performClickById(id: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performClick(list)
            }
        }

        /**
         *   * 通过Text查找节点，点击该节点
         */
        fun performClickByTag(tag: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
                performClick(list)
            }
        }

        /**
         * 执行点击
         */
        private fun performClick(list: List<AccessibilityNodeInfo>) {
            if (list.isEmpty() || list.size > 1) AppLog.e("===== size ${list.size}")
            for (i in list.indices) {
                val node: AccessibilityNodeInfo = list[i]
                AppLog.d("index: $i className: ${node.className}   text:${node.text}  ")
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }
        }

        /**
         * 设置文本 通过ID
         */
        fun performSetTextToInputById(id: String, text: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performSetTextToNodes(list, text)
            }
        }

        /**
         * 设置文本 通过Text
         */
        fun performSetTextToInputByTag(tag: String, text: String) {
            service?.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
                performSetTextToNodes(list, text)
            }

        }

        /**
         * 设置文本
         */
        private fun performSetTextToNodes(list: List<AccessibilityNodeInfo>, text: String) {
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
                    this.dispatchGesture(gestureDescription, object : AccessibilityService.GestureResultCallback() {
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

        /**
         * 判断辅助服务是否开启
         */
         fun isAccessibilityEnabled(context: Context): Boolean {
            val am = context.getSystemService(MyBaseActivity.ACCESSIBILITY_SERVICE) as AccessibilityManager
            return am.isEnabled
        }

         fun lunchApp(aty: Activity, packageName: String) {
            try {
                val intent = aty.packageManager.getLaunchIntentForPackage(packageName)
                intent!!.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.action = Intent.ACTION_MAIN
                aty.startActivity(intent)
            } catch (ex: Exception) {
                ToastUtil.showLong("启动apk发生异常... ")
                AppLog.e(ex)
            }
        }
    }
}