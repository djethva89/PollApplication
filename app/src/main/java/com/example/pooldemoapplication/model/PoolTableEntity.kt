package com.example.pooldemoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pool_table")
data class PoolTableEntity(
    @ColumnInfo(name = "pool_name")
    val poolName: String,
    @ColumnInfo(name = "create_at")
    val createAt: String,
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
}

