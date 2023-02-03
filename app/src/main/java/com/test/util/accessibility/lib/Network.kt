package com.test.util.accessibility.lib

import android.os.SystemClock
import com.test.util.box_obj.ObjectBox
import com.test.util.utils.AppLog
import io.objectbox.Box
import org.redisson.Redisson
import org.redisson.api.RList
import org.redisson.api.RedissonClient
import org.redisson.client.codec.StringCodec
import org.redisson.config.Config
import org.redisson.connection.ConnectionListener
import java.net.InetSocketAddress
import java.util.Date

class Network {

    private var mRedisson: RedissonClient? = null
    var isRunning: Boolean = false
    private var taskStartTime: Long = 0
    private val taskRecorderBox: Box<NewTaskRecorder> = ObjectBox.store.boxFor(NewTaskRecorder::class.java)
    private var lastInsertDataId: Long? = null
    private var uploadEntity: UploadEntity? = null

    fun connect() {
        isRunning = true
        AppLog.i("Redisson ===== connect ================")
        try {
            if (mRedisson == null || mRedisson!!.isShutdown) {
                val config = Config()
                config.codec = StringCodec()
                config.connectionListener = object : ConnectionListener {
                    override fun onConnect(addr: InetSocketAddress?) {
                        AppLog.i("Redisson onConnect OK", " ${addr?.hostString}")
                    }

                    override fun onDisconnect(addr: InetSocketAddress?) {
                        AppLog.i("Redisson onConnect Fail", " ${addr?.hostString}")
                    }
                }
                val address = "redis://"
                val password = "123456"
                val masterName = "tom"
                AppLog.i("链接信息： address:$address  password:$password  masterName:$masterName")
                config.useSentinelServers().setCheckSentinelsList(false)
                    .setMasterName(masterName)
                    .addSentinelAddress("redis://${address}").password =
                    password
                mRedisson = Redisson.create(config)
                AppLog.i("网络链接初始化完毕： mRedisson: $mRedisson")
                if (taskStartTime == 0L) {
                    taskStartTime = System.currentTimeMillis()
                }
                if (uploadEntity != null) {
                    AppLog.i("准备上传上次上传失败的数据")
                    uploadTask(uploadEntity!!)
                }
                startTask()
            } else {
                AppLog.d("已经链接.")
            }
        } catch (e: Exception) {
            mRedisson = null
            AppLog.e(e)
        }
    }

    fun uploadTask(upEntity: UploadEntity) {
        try {
            uploadEntity = upEntity
            if (mRedisson != null && !mRedisson!!.isShutdown) {
                val list: RList<String>? = mRedisson?.getList("bankcard:validate:res")
                val text =
                    "${upEntity.entity.id}|${upEntity.res.code}|${upEntity.summary}|${upEntity.realName}|${upEntity.taskType}"
                list!!.add(text)
                AppLog.i("上传数据成功： $text")
                uploadEntity = null
                updateDatabase(upEntity.res.code)
                if (isRunning) {
                    startTask()
                } else {
                    AppLog.i("isRunning = false 停止自动化任务1 ")
                }
            } else {
                AppLog.e("准备上传数据异常. 链接已经断开, 准备重新链接.")
                reconnect()
            }
        } catch (e: Exception) {
            AppLog.e("上传数据异常... ???")
        }
    }

    private var executeTimes: Long = 0
    private fun startTask() {
        if (!isRunning) {
            AppLog.i("停止运行...")
            return
        }
        executeTimes++
        if (mRedisson != null && !mRedisson!!.isShutdown) {
            val str: String? = mRedisson?.getDeque<String>("bankcard:validate:pub")?.pollLast()
            if (!str.isNullOrEmpty()) {
                val strArray = str.split("|")
                if (strArray.last() != "deviceId") {
                    val entity = TaskEntity(
                        id = strArray[0],
                        bankCode = strArray[1],
                        bankCard = strArray[2],
                        name = strArray[3]
                    )
                    insertDatabase(entity)
                    val curNewTask = TasKManager.getCurNewTask()
                    if (curNewTask == null) {
                        AppLog.e("异常，获取的 curNewTask为Null")
                    } else {
                      //  curNewTask.start(aty = CommApp.app, taskEntity = entity, netWork = this)
                    }
                }
            } else {
                AppLog.i("无任务 str: $str  执行次数：$executeTimes")
                SystemClock.sleep(1000)
                if (isRunning) {
                    startTask()
                } else {
                    AppLog.i("isRunning = false 停止自动化任务2 ")
                }
            }
        } else {
            AppLog.e("准备获取任务异常，服务器断开了...开始重新链接服务器.$executeTimes")
            reconnect()
        }
    }

    private fun reconnect() {
        if (isRunning) {
            connect()
        } else {
            AppLog.i("isRunning = false 停止自动化任务")
        }
    }

    private fun insertDatabase(entity: TaskEntity) {
        lastInsertDataId = taskRecorderBox.put(
            NewTaskRecorder(
                taskId = entity.id,
                bankCode = entity.bankCode,
                bankCard = entity.bankCard,
                name = entity.name,
                time = Date().toString(),
                result = null,
                msg = null
            )
        )
    }

    private fun updateDatabase(code: Int) {
        if (lastInsertDataId != null) {
            val data: NewTaskRecorder = taskRecorderBox.get(lastInsertDataId!!)
            data.result = "" + code
            taskRecorderBox.put(data)
            lastInsertDataId = null
        }
    }

    fun stop() {
        if (mRedisson != null && !mRedisson!!.isShutdown) {
            mRedisson!!.shutdown()
            isRunning = false
        }
    }
}