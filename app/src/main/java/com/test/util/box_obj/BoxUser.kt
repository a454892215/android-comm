package com.test.util.box_obj

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class BoxUser(
    @Id
    var id: Long = 0,
    var name: String,
    var age: Int,
    var msg: String
)
