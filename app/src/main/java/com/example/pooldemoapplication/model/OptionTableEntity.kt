package com.example.pooldemoapplication.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "option_table", foreignKeys = [ForeignKey(
        entity = PoolTableEntity::class,
        parentColumns = ["id"],
        childColumns = ["pool_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OptionTableEntity(
    @ColumnInfo(name = "pool_id") var poolId: Long? = null,
    @ColumnInfo(name = "option_name") val optionName: String,
    @ColumnInfo(name = "percentage") val percentage: String,
    @ColumnInfo(name = "create_at") val createAt: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}


