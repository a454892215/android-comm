package com.test.util.accessibility.lib

import android.os.SystemClock
import com.common.utils.ToastUtil
import com.test.util.utils.AppLog
import org.redisson.Redisson
import org.redisson.api.RList
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import org.redisson.connection.ConnectionListener
import java.net.InetSocketAddress

class Network {

    private var mRedisson: RedissonClient? = null
    var isRunning: Boolean = false
    var taskStartTime: Long = 0
   // var timeComputer: TimeComputeUtil = TimeComputeUtil()

    fun connect() {
        try {
            if (mRedisson == null || mRedisson!!.isShutdown) {
                val config = Config()
                config.codec = StringCodec()
                config.connectionListener = object : ConnectionListener {
                    override fun onConnect(addr: InetSocketAddress?) {
                        isRunning = true
                        AppLog.i("Redisson 链接成功", " isShutdown:${mRedisson!!.isShutdown}")
                        taskStartTime = System.currentTimeMillis()
                        startTask()
                    }

                    override fun onDisconnect(addr: InetSocketAddress?) {
                        isRunning = false
                        ToastUtil.showLong("Redisson 链接失败")
                        AppLog.e("Redisson 链接失败", " ${addr?.hostString}")
                    }
                }
                val address = "redis://${""}"
                val password = "${""}"
                val masterName = "${""}"
                AppLog.i("链接信息： address:$address  password:$password  masterName:$masterName")
                config.useSentinelServers().setCheckSentinelsList(false)
                    .setMasterName(masterName)
                    .addSentinelAddress(address).password = password

                mRedisson = Redisson.create(config)
            } else {
                AppLog.d("已经链接.")
            }
        } catch (e: Exception) {
            AppLog.e(e)
        }
    }

    fun uploadTask(entity: TaskEntity, res: VerifyResult, taskType: String, summary: String, realName: String) {
        val list: RList<String>? = mRedisson?.getList("bankcard:validate:res")
        val text = "${entity.id}|${summary}|${realName}|${taskType}"
        list!!.add(text)
        AppLog.i("上传内容成功： $text")
        startTask()
    }

    var executeTimes: Long = 0
    fun startTask() {
        if (!isRunning) return
        executeTimes++
        if (mRedisson != null && !mRedisson!!.isShutdown) {
            val str: String? = mRedisson?.getDeque<String>("bankcard:validate:pub")?.pollLast()
            if (!str.isNullOrEmpty()) {
                val strArray = str.split("|")
                val entity = TaskEntity(
                    id = strArray[0],
                    bankCode = strArray[1],
                    bankCard = strArray[2],
                    name = strArray[3]
                )
            } else {
                AppLog.i("无任务 str: $str  执行次数：$executeTimes")
                SystemClock.sleep(1000)
                startTask()
            }
        } else {
            AppLog.e("异常服务器断开了...开始重新链接服务器.$executeTimes")
            connect()
        }
    }

    fun stop() {
        if (mRedisson != null && !mRedisson!!.isShutdown) {
            mRedisson!!.shutdown()
            isRunning = false
        }
    }
}