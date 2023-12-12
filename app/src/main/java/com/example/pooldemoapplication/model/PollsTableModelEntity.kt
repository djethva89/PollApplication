package com.example.pooldemoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

open class PollsTableModelEntity(
    open val poolName: String,
    open val createAt: Long,
    open var isGivePercentage: Boolean = false,
) {
    open var id: Long? = null
}

