package com.example.pooldemoapplication.config.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "pool_table")
data class PollsTableModel(
    @ColumnInfo(name = "pool_name")
    val poolName: String,
    @ColumnInfo(name = "create_at")
    val createAt: Long,
    @ColumnInfo(name = "is_give_percentage")
    var isGivePercentage: Boolean = false,
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

    @Ignore
    var oldIndex: Int = -1

    @Ignore
    var newIndex: Int = -1
}

