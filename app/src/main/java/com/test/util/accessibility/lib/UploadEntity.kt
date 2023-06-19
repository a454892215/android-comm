package com.test.util.accessibility.lib

data class UploadEntity(
    val entity: TaskEntity,
    val res: VerifyResult,
    val taskType: String,
    val summary: String,
    val realName: String,
)

