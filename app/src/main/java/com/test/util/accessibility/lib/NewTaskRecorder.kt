package com.test.util.accessibility.lib

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class NewTaskRecorder(
    @Id
    var id: Long = 0,
    var taskId: String,
    var bankCode: String,
    var bankCard: String,
    var name: String,
    var time: String,
    var result: String?,
    var msg: String?,
)
