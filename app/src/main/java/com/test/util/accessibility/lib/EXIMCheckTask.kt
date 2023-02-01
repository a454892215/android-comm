package com.test.util.accessibility.lib

import android.app.Activity
import android.os.SystemClock
import android.view.accessibility.AccessibilityNodeInfo
import com.auto.pay.task2.*
import com.common.utils.AssertsUtil
import com.google.gson.Gson
import com.test.util.utils.AppLog

class EXIMCheckTask(override var curJsonItemIndex: Int = -1) : BaseTask {
    private var verifyResult: VerifyResult = VerifyResult.Except

    companion object {
        private const val appId: String = "com.appid"
        private const val password: String = "..."
        var isInToAccountPage: Boolean = false
        var hasExecuteTaskSize: Int = 0
        var failTaskSize: Int = 0
        var exceptionTaskSize: Int = 0
        val mapData: MutableMap<String, String> = mutableMapOf()
        var listData: List<KeyValueEntity> = mutableListOf()

        fun getFullId(id: String): String {
            return "$appId:id/$id"
        }
    }

    private var netWork: Network? = null

    override fun start(aty: Activity, taskEntity: TaskEntity, netWork: Network) {
        this.netWork = netWork
        verifyResult = VerifyResult.Except // 默认异常
        AppLog.d("任务启动：任务线程：threadName" + Thread.currentThread().name)
        initData()
        val start = System.currentTimeMillis()
        try {
            if (ServiceUtil.getService() == null) {
                AppLog.e("请先启动服务辅助...")
                return
            }
            val nodeFinder = NodeFinder()
            if (!isInToAccountPage) {
                startAppAndToAccountPage(aty, nodeFinder)
            } else {
                AppLog.d("已经在转账页面...")
            }
            onEnterToAccountPage(nodeFinder, taskEntity)
            val costTime = (System.currentTimeMillis() - start) / 1000
            hasExecuteTaskSize++
            AppLog.i("任务执行完成... costTime:${costTime}s  " + getExecutedTaskSummary())
        } catch (e: Exception) {
            AppLog.e(e)
            isInToAccountPage = false
            // ServiceUtil.killByAppId(appId)
            uploadExceptionResult(taskEntity, "代码执行异常")
        }
    }

    private fun getExecutedTaskSummary(): String {
        return "已执行任务数：$hasExecuteTaskSize  异常任务数：$exceptionTaskSize  失败任务数：$failTaskSize"
    }

    private fun startAppAndToAccountPage(aty: Activity, nodeFinder: NodeFinder) {
        ServiceUtil.lunchApp(aty = aty, appId = appId)
        SystemClock.sleep(2000)
        val loginNode = nodeFinder.getNodeByTag("Login")
        ServiceUtil.performClick(loginNode!!, false)

        val passwordNode = getPasswordNode(nodeFinder)
        ServiceUtil.setText(passwordNode!!, password)

        val btnLogin = nodeFinder.getNodeById(getFullId("btnLogin"))
        ServiceUtil.performClick(btnLogin!!, false)

        val transfer = nodeFinder.getNodeByTag("Money Transfer")
        ServiceUtil.performClick(transfer!!, false)

        val toAccount = nodeFinder.getNodeByTag("To account")
        ServiceUtil.performClick(toAccount!!, false)
    }

    private fun onEnterToAccountPage(nodeFinder: NodeFinder, taskEntity: TaskEntity) {
        val ivBene = nodeFinder.getNodeById(getFullId("ivBene"), false, 0)
        ServiceUtil.performClick(ivBene!!) // 3. 进入银行卡选择列表
        isInToAccountPage = true

        val searchNode = getSearchNode(nodeFinder)
        val bankValue = mapData[taskEntity.bankCode]
        ServiceUtil.setText(searchNode!!, bankValue!!)
        nodeFinder.setMaxExeTimes(3)
        SystemClock.sleep((10L..15L).random() * 10 * 6)
        val rv = nodeFinder.getNodeById(getFullId("recyclerView"))
        if (rv != null && rv.childCount == 1) {
            AppLog.d("$taskEntity 银行code 校验OK")
            rv.getChild(0).performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else {
            AppLog.e("$taskEntity  银行code 校验Fail")
        }
        nodeFinder.setMaxExeTimes(20)
        SystemClock.sleep((300L..350L).random())
        val cardNumNode = nodeFinder.getNodeById(getFullId("txtContent"), tarNodeIndex = 1)
        cardNumNode!!.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        ServiceUtil.setText(cardNumNode, taskEntity.bankCard)
        cardNumNode.performAction(AccessibilityNodeInfo.ACTION_CLEAR_FOCUS)
        SystemClock.sleep(200)
        nodeFinder.setMaxExeTimes(6)
        val invalidNode = nodeFinder.getNodeByTag("Invalid beneficiary account", false, 0, true)
        if (invalidNode != null) {
            AppLog.i("无效卡号")
            verifyResult = VerifyResult.Fail
            val node = ServiceUtil.getNodeByTag("Close")
            node!!.performAction(AccessibilityNodeInfo.ACTION_CLICK)
            SystemClock.sleep(300)
            failTaskSize++
            upload(taskEntity, "InvalidCardName")
            return
        }
        SystemClock.sleep(200)
        val validNode = ServiceUtil.getNodeById(getFullId("tvName"))
        if (validNode != null) {
            val text: String = validNode.text.toString()
            val realName = StringUtils.removeAccent(text.trim()).replace("\\s+".toRegex(), "").lowercase()
            val checkName = StringUtils.removeAccent(taskEntity.name.trim()).replace("\\s+".toRegex(), "").lowercase()
            verifyResult = if (checkName.equals(realName, true)) {
                AppLog.i("有效卡号：text: ${validNode.text} ")
                VerifyResult.OK
            } else {
                AppLog.i("无效有效卡号：name校验失败: ${validNode.text} ")
                VerifyResult.Fail2
            }
            upload(taskEntity, realName)
            return
        }
        // 有效无效都没有进入，
        uploadExceptionResult(taskEntity, "非有效非无效异常")
    }

    private fun uploadExceptionResult(taskEntity: TaskEntity, realName: String) {
        if (verifyResult == VerifyResult.Except) {
            exceptionTaskSize++
            upload(taskEntity, realName)
            AppLog.e("处理完毕，流程异常...")
        }
    }

    private fun upload(taskEntity: TaskEntity, realName: String) {
        val curTaskName = TasKManager.getCurTaskName()!!
        val uploadEntity =
            UploadEntity(
                entity = taskEntity,
                res = verifyResult,
                taskType = curTaskName,
                summary = verifyResult.name,
                realName = realName
            )
        netWork!!.uploadTask(uploadEntity)
    }

    @SuppressWarnings("unused")
    private fun verifyLocalBankData(searchNode: AccessibilityNodeInfo) {
        val list: List<KeyValueEntity> = getJsonList()
        for (index in (35 until list.size)) {
            ServiceUtil.setText(searchNode, list[index].value)
            SystemClock.sleep((10L..15L).random() * 100 * 2)
            val rv = ServiceUtil.getNodeById(getFullId("recyclerView"))
            if (rv != null && rv.childCount == 1) {
                AppLog.d("$index  ${list[index]}  校验OK.")
            } else {
                AppLog.e("$index  ${list[index]}  校验失败.")
                return
            }
            SystemClock.sleep((25L..30L).random() * 10 * 2)
        }
        AppLog.i("全部数据校验完毕。")
    }

    override fun getSearchNode(nodeFinder: NodeFinder): AccessibilityNodeInfo? {
        try {
            return nodeFinder.getNodeById(getFullId("edtSearchDialog"))
        } catch (e: Exception) {
            AppLog.e(e)
        }
        return null
    }

    override fun getPasswordNode(nodeFinder: NodeFinder): AccessibilityNodeInfo? {
        return nodeFinder.getNodeById(getFullId("tvPassword"))
    }

    override fun getLocalJsonData(): List<KeyValueEntity> {
        val json = AssertsUtil.getText("exim.json")
        return  Gson().fromJson(json, Array<KeyValueEntity>::class.java).toList()
    }

    override fun initData() {
        if (mapData.isEmpty()) {
            val list: List<KeyValueEntity> = getLocalJsonData()
            for (item in list) {
                mapData[item.key] = item.value
            }
            listData = list
            AppLog.i("json 数据初始化完毕：size: ${mapData.size}")
        }
    }

    override fun getJsonList(): List<KeyValueEntity> {
        if (listData.isEmpty()) {
            initData()
        }
        return listData
    }

    override fun getAppId(): String {
        return appId
    }

    override fun getPassword(): String {
        return password
    }

}