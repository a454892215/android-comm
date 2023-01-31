package com.test.util.accessibility.lib

import com.common.utils.ToastUtil

class TasKManager {

    companion object {
        private fun getCurTask(): BaseTask? {
            val index = 0 //TODO
            if (index == 5) {
                return EXIMCheckTask()
            }
            return null
        }

        fun inputNextValue(dir: Int) {
            if (ServiceUtil.getService() == null) {
                ToastUtil.showLong("请先打开辅助服务！")
                // val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                // CommApp.mainActivity.startActivity(intent)
                return
            }
            val curTask: BaseTask? = getCurTask()
            if (curTask != null) {
                curTask.initData()
                if (dir > 0) {
                    curTask.curJsonItemIndex++
                } else {
                    curTask.curJsonItemIndex--
                }
                if (curTask.curJsonItemIndex >= curTask.getJsonList().size) {
                    curTask.curJsonItemIndex = -1
                } else if (curTask.curJsonItemIndex <= -1) {
                    curTask.curJsonItemIndex = curTask.getJsonList().size - 1
                }
                val nodeFinder = NodeFinder()
                nodeFinder.setIsAutoMode(false)
                nodeFinder.setMaxExeTimes(1)
                val searchNode = curTask.getSearchNode(nodeFinder)
                if (searchNode == null) {
                    ToastUtil.showLong("当前页面没有找到输入银行代号输入框")
                } else {
                    val bankValue = curTask.getJsonList()[curTask.curJsonItemIndex].value
                    ServiceUtil.setText(searchNode, bankValue)
                }
            } else {
                ToastUtil.showLong("异常没有找到目标task")
            }
        }

        fun getCurJsonItemIndex(): Int {
            val curTask: BaseTask? = getCurTask()
            return curTask?.curJsonItemIndex ?: -2
        }
    }


}