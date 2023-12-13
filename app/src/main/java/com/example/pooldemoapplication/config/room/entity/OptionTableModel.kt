package com.example.pooldemoapplication.config.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "option_table", foreignKeys = [ForeignKey(
        entity = PollsTableModel::class,
        parentColumns = ["id"],
        childColumns = ["pool_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class OptionTableModel(
    @ColumnInfo(name = "pool_id")
    var poolId: Long? = null,
    @ColumnInfo(name = "option_name")
    val optionName: String,
    @ColumnInfo(name = "percentage")
    var percentage: Int = 0,
    @ColumnInfo(name = "create_at")
    val createAt: Long
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}


