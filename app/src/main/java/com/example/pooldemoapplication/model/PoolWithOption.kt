package com.example.pooldemoapplication.model

import androidx.room.Embedded
import androidx.room.Relation

data class PoolWithOption(

    @Embedded
    var poolTableEntity: PoolTableEntity? = null,

    @Relation(entity = OptionTableEntity::class, parentColumn = "id", entityColumn = "pool_id")
    var optionTableEntity: List<OptionTableEntity>? = null
)