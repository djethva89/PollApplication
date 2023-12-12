package com.example.pooldemoapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.pooldemoapplication.model.OptionTableEntity
import com.example.pooldemoapplication.model.PoolTableEntity
import com.example.pooldemoapplication.model.PoolWithOption
import com.example.pooldemoapplication.repository.PoolRepository

class PoolViewModel : ViewModel() {
    var poolList: LiveData<MutableList<PoolWithOption>?>? = null

    fun insertPoolWithOption(
        context: Context,
        poolTableEntity: PoolTableEntity,
        optionTableEntity: List<OptionTableEntity>
    ) {
        PoolRepository.insertPoolWithOption(context, poolTableEntity, optionTableEntity)

    }

    fun getPoolWithOption(
        context: Context,
    ): LiveData<MutableList<PoolWithOption>?>? {
        poolList = PoolRepository.getPoolWithOption(context)

        return poolList
    }

}