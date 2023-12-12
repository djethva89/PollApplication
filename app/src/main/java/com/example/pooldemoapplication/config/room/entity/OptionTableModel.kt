package com.example.pooldemoapplication.config.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.pooldemoapplication.model.OptionTableModelEntity

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
    override var poolId: Long? = null,
    @ColumnInfo(name = "option_name")
    override val optionName: String,
    @ColumnInfo(name = "percentage")
    override var percentage: Int = 0,
    @ColumnInfo(name = "create_at")
    override val createAt: Long
) : OptionTableModelEntity(poolId, optionName, percentage, createAt) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Int? = null
}


