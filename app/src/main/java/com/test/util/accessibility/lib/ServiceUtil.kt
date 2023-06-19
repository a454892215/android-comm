package com.test.util.accessibility.lib

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
import androidx.annotation.RequiresApi
import com.common.comm.L
import com.test.util.utils.AppLog


@SuppressWarnings("unused")
class ServiceUtil {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var service: AccessibilityService? = null

        fun getService(): AccessibilityService? {
            return service
        }

        fun init(obj: AccessibilityService) {
            service = obj
            AppLog.i("ServiceUtil 初始化完毕... ")
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
            node!!.apply {
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

        @SuppressWarnings("unused")
        fun printIdForAllNode(node: AccessibilityNodeInfo?) {
            node!!.apply {
                if (node.childCount > 0) {
                    for (i in 0 until node.childCount) {
                        val v = node.getChild(i)
                        if (v.viewIdResourceName != null && v.viewIdResourceName.isNotEmpty()) {
                            AppLog.d("Id: ${v.viewIdResourceName}")
                        }
                        printIdForAllNode(v)
                    }
                }
            }
        }

        @SuppressWarnings("unused")
        fun printTextForAllNode(node: AccessibilityNodeInfo?) {
            node!!.apply {
                if (node.childCount > 0) {
                    for (i in 0 until node.childCount) {
                        val v = node.getChild(i)
                        if (v.text != null && v.text.isNotEmpty()) {
                            AppLog.d("text: ${v.text}")
                        }
                        printTextForAllNode(v)
                    }
                }
            }
        }


        @SuppressWarnings("unused")
        fun printInfoForAllClickableNode(node: AccessibilityNodeInfo?) {
            node!!.apply {
                if (node.childCount > 0) {
                    for (i in 0 until node.childCount) {
                        val v = node.getChild(i)
                        if (v.isClickable) {
                            AppLog.d("text: ${v.text}  Id:${v.viewIdResourceName}")
                        }
                        printInfoForAllClickableNode(v)
                    }
                }
            }
        }

        // 从指定节点的父节点开始迭代父节点所有子节点 ，逐层向外迭代
        @SuppressWarnings("unused")
        fun printClickableChildNodeInfoFromOuter(node: AccessibilityNodeInfo) {
            AppLog.i("开始打印第1层节点...")
            var list: List<AccessibilityNodeInfo> = getAllNodeFromRoot(node.parent)
            printClickableNodeInfoForList(list)
            AppLog.i("开始打印第2层节点...")
            list = getAllNodeFromRoot(node.parent.parent)
            printClickableNodeInfoForList(list)
            AppLog.i("开始打印第3层节点...")
            list = getAllNodeFromRoot(node.parent.parent.parent)
            printClickableNodeInfoForList(list)
        }

        private fun printClickableNodeInfoForList(list: List<AccessibilityNodeInfo>, tag: Int = 0) {
            for (node in list) {
                if (node.isClickable) {
                    AppLog.d("text: ${node.text}  Id:${node.viewIdResourceName}")
                }
            }
        }

        /**
         * 通过hintText查找节点设置文本，要求Android 8.0版本以上
         */
        @RequiresApi(Build.VERSION_CODES.O)
        fun performSetTextToInputByHintText(hintText: String, text: String) {
            val root: AccessibilityNodeInfo? = service?.rootInActiveWindow
            val list: List<AccessibilityNodeInfo> = getAllNodeFromRoot(root)
            for (node in list) {
                if (node.hintText != null && node.hintText.toString() == hintText) {
                    performSetTextToNodes(listOf(node), text)
                    break
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
            service!!.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performClick(list)
            }
        }

        fun getNodeByTag(tag: String, tartIndex: Int = -1): AccessibilityNodeInfo? {
            val rootInActiveWindow = service!!.rootInActiveWindow ?: return null
            val list: List<AccessibilityNodeInfo> = rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
            if (tartIndex > -1) {
                return if (list.size > tartIndex) {
                    list[tartIndex]
                } else {
                    null
                }
            }
            val tarList: MutableList<AccessibilityNodeInfo> = ArrayList()
            for (item in list) {
                if (item.text == tag) {
                    tarList.add(item)
                }
            }
            if (tarList.isEmpty() || tarList.size != 1) {
                AppLog.d("符合条件的节点数目，不等于1 size: ${list.size} tarSize: ${tarList.size} tag:$tag")
            } else {
                return tarList[0]
            }
            return null
        }


        fun getNodeById(id: String, tartIndex: Int = -1): AccessibilityNodeInfo? {
            val rootInActiveWindow = service!!.rootInActiveWindow ?: return null
            val list: List<AccessibilityNodeInfo> = rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
            if (tartIndex > -1) {
                return if (list.size > tartIndex) {
                    list[tartIndex]
                } else {
                    null
                }
            }
            val tarList: MutableList<AccessibilityNodeInfo> = ArrayList()
            for (item in list) {
                if (item.viewIdResourceName == id) {
                    tarList.add(item)
                }
            }
            if (tarList.isEmpty() || tarList.size != 1) {
                AppLog.d("符合条件的节点数目，不等于1 size ${tarList.size}  id=$id")
            } else {
                return tarList[0]
            }
            return null
        }

        fun getFocusedNode(): AccessibilityNodeInfo? {
            val list = getAllNodeFromRoot(service!!.rootInActiveWindow)
            for (item in list) {
                if (item.isFocused) {
                    return item
                }
            }
            return null
        }

        fun getNodeByIdAndIndex(id: String, index: Int): AccessibilityNodeInfo? {
            service!!.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                val tarList: MutableList<AccessibilityNodeInfo> = ArrayList()
                for (item in list) {
                    if (item.viewIdResourceName == id) {
                        tarList.add(item)
                    }
                }
                if (tarList.size > index) {
                    return tarList[index]
                } else {
                    AppLog.e("没有找到目标节点 tarSize ${tarList.size}  id=$id")

                }
            }
            return null
        }

        fun getNodeByTagAndClick(tag: String, isPrint: Boolean = false) {
            val node = getNodeByTag(tag)
            performClick(node!!, isPrint)
        }

        fun getNodeByIdAndClick(id: String, isPrint: Boolean = false) {
            val node = getNodeById(id)
            performClick(node!!, isPrint)
        }

        fun performClick(node: AccessibilityNodeInfo, isPrint: Boolean = false) {
            if (node.isClickable) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                if (isPrint) {
                    AppLog.d("节点被点击 ID: ${node.viewIdResourceName}")
                }
                return
            } else {
                performClick(node.parent, isPrint)
            }
        }

        fun setText(node: AccessibilityNodeInfo, text: String) {
            val arguments = Bundle()
            arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, text)
            node.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments)
        }

        /**
         *   * 通过Text查找节点，点击该节点
         */
        fun performClickByTag(tag: String, expectSize: Int = 1) {
            service!!.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByText(tag)
                val tarList: MutableList<AccessibilityNodeInfo> = ArrayList()
                for (item in list) {
                    if (item.text == tag) {
                        tarList.add(item)
                    }
                }
                if (tarList.isEmpty() || tarList.size != expectSize) {
                    AppLog.e("符合条件的节点数目，不等于期望数 size ${list.size}")
                } else {
                    performClick(tarList)
                }

            }
        }

        /**
         * 执行点击
         */
        private fun performClick(list: List<AccessibilityNodeInfo>) {

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
            service!!.apply {
                val list: List<AccessibilityNodeInfo> = this.rootInActiveWindow.findAccessibilityNodeInfosByViewId(id)
                performSetTextToNodes(list, text)
            }
        }

        /**
         * 设置文本 通过Text
         */
        fun performSetTextToInputByTag(tag: String, text: String) {
            service!!.apply {
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
                    AppLog.e(" ==== 手机最低版本号必须大于24 ===== ")
                }
            }
        }

        fun lunchApp(aty: Activity, appId: String) {
            val intent = aty.packageManager.getLaunchIntentForPackage(appId)
            intent!!.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.action = Intent.ACTION_MAIN
            aty.startActivity(intent)
            AppLog.d("启动app完成： $appId")
        }

        /**
         * 判断辅助服务是否开启
         */
        fun isAccessibilityEnabled(context: Context): Boolean {
            val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
            return am.isEnabled
        }

//        fun killByAppId(appId: String) {
//            try {
//                val info1 = PerformAbility.findSoleText(
//                    service?.rootInActiveWindow,
//                    "关闭应用"
//                )
//                if (info1 != null) {
//                    Thread.sleep(1000)
//                    PerformAbility.performClick(info1)
//                    AppLog.i("使用节点方式原本银行app")
//                    return
//                }
//                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                intent.data = Uri.fromParts("package", appId, null)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                service?.startActivity(intent)
//                SystemClock.sleep(1000)
//                val info = PerformAbility.findSoleId(
//                    service?.rootInActiveWindow,
//                    "com.android.settings:id/right_button"
//                )
//                if (info != null) {
//                    SystemClock.sleep(500)
//                    if (PerformAbility.performClick(info)) {
//                        SystemClock.sleep(300)
//                        if (PerformAbility.performClick(PerformAbility.findSoleId(service?.rootInActiveWindow, "android:id/button1"))) {
//                            SystemClock.sleep(1000)
//                            service?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
//                        }
//                        SystemClock.sleep(500)
//                    }
//                }
//                SystemClock.sleep(500)
//                AppLog.d("关闭银行app完毕...")
//            } catch (e: Exception) {
//                AppLog.e(e)
//            }
//        }
    }
}