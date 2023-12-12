package com.example.pooldemoapplication.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import com.example.pooldemoapplication.repository.PollsRepository

class PollsViewModel : ViewModel() {
    var poolList: LiveData<List<PollsWithOption>?>? = null

    fun insertPoolWithOption(
        context: Context,
        pollsTableModel: PollsTableModel,
        optionTableEntity: List<OptionTableModel>
    ) {
        PollsRepository.insertPoolWithOption(context, pollsTableModel, optionTableEntity)

    }

    fun getPoolWithOption(
        context: Context,
    ): LiveData<List<PollsWithOption>?>? {
        poolList = PollsRepository.getPoolWithOption(context)

        return poolList
    }

}