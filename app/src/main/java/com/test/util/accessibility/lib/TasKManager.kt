package com.test.util.accessibility.lib

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import com.common.utils.ToastUtil
import com.test.util.utils.AppLog


class TasKManager {

    companion object {
        private val mapTask: MutableMap<Int, BaseTask?> = mutableMapOf()

        private fun getCurCacheTask(): BaseTask? {
            var ret: BaseTask? = null
            val index = 0
            if (mapTask[index] != null) {
                return mapTask[index]
            }
            if (index == 5) {
                ret = EXIMCheckTask()
            }
            mapTask[index] = ret
            return ret
        }

        fun getCurNewTask(): BaseTask? {
            var ret: BaseTask? = null
            val index = 0
            if (index == 5) {
                ret = EXIMCheckTask()
            }
            return ret
        }

        fun getCurTaskName(): String? {
            var ret: String? = null
            val index = 0
            if (index == 5) {
                ret = "exim"
            }
            if (ret == null) {
                AppLog.e("异常获取的任务名字是null")
            }
            return ret
        }

        fun inputNextValue(dir: Int) {
            if (checkAcceService()) return
            val curTask: BaseTask? = getCurCacheTask()
            if (curTask != null) {
                curTask.initData()
                if (dir > 0) {
                    curTask.curJsonItemIndex++
                } else {
                    curTask.curJsonItemIndex--
                }
                if (curTask.curJsonItemIndex >= curTask.getJsonList().size) {
                    curTask.curJsonItemIndex = 0
                } else if (curTask.curJsonItemIndex <= -1) {
                    curTask.curJsonItemIndex = curTask.getJsonList().size - 1
                }

                val searchNode = curTask.getSearchNode(getNodeFinder())
                if (searchNode == null) {
                    ToastUtil.showLong("当前页面没有找到输入银行代号输入框")
                } else {
                    AppLog.d("curJsonItemIndex: ${curTask.curJsonItemIndex}")
                    val bankValue = curTask.getJsonList()[curTask.curJsonItemIndex].value
                    ServiceUtil.setText(searchNode, bankValue)
                }
            } else {
                ToastUtil.showLong("异常没有找到目标task")
            }
        }

        private fun checkAcceService(): Boolean {
            if (ServiceUtil.getService() == null) {
                ToastUtil.showLong("请先打开辅助服务！")
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                // CommApp.mainActivity.startActivity(intent)
                return true
            }
            return false
        }

        fun startApp(aty: Activity) {
            val curTask = getCurCacheTask()
            if (curTask != null) {
                ServiceUtil.lunchApp(aty = aty, appId = curTask.getAppId())
            }
        }

        fun inputPassword() {
            if (checkAcceService()) return
            val curTask: BaseTask? = getCurCacheTask()
            if (curTask != null) {
                val passwordNode = curTask.getPasswordNode(getNodeFinder())
                if (passwordNode == null) {
                    val txt = "没有找到密码输入框，请确定是否在目标页面"
                    ToastUtil.showLong(txt)
                    AppLog.e(txt)
                } else {
                    ServiceUtil.setText(passwordNode, curTask.getPassword())
                }
            } else {
                val text = "没有找到对应的curTask..."
                AppLog.d(text)
                ToastUtil.showLong(text)
            }
        }

        fun getCurJsonItemIndex(): Int {
            val curTask: BaseTask? = getCurCacheTask()
            return curTask?.curJsonItemIndex ?: -2
        }

        private fun getNodeFinder(): NodeFinder {
            val nodeFinder = NodeFinder()
            nodeFinder.setMaxExeTimes(1)
            return nodeFinder
        }
    }

}