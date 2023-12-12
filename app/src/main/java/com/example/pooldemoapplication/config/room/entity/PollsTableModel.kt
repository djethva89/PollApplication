package com.example.pooldemoapplication.config.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pooldemoapplication.model.PollsTableModelEntity

@Entity(tableName = "pool_table")
data class PollsTableModel(

    @ColumnInfo(name = "pool_name")
    override val poolName: String,
    @ColumnInfo(name = "create_at")
    override val createAt: Long,
    @ColumnInfo(name = "is_give_percentage")
    override var isGivePercentage: Boolean = false,

    ) : PollsTableModelEntity(poolName, createAt, isGivePercentage) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Long? = null
}

