package com.example.pooldemoapplication.config.room.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PollsWithOption(

    @Embedded
    var pollsTableModel: PollsTableModel? = null,

    @Relation(entity = OptionTableModel::class, parentColumn = "id", entityColumn = "pool_id")
    var optionTableEntity: List<OptionTableModel>? = null
)