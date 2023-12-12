package com.example.pooldemoapplication.model

open class OptionTableModelEntity(

    open val poolId: Long? = null,
    open val optionName: String,
    open val percentage: Int = 0,
    open val createAt: Long
) {
    open val id: Int? = null
}

