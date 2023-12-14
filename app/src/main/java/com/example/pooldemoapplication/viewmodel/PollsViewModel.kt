package com.example.pooldemoapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import com.example.pooldemoapplication.repository.PollsRepository
import com.example.pooldemoapplication.ui.currentPolls.CurrentPollsFragment

class PollsViewModel : ViewModel() {

    private val _pollWithOptionList = MutableLiveData<List<PollsWithOption>?>().apply {
        value = emptyList()
    }

    val pollWithOptionList: LiveData<List<PollsWithOption>?> = _pollWithOptionList

    private fun updatePollList(pollsWithOptions: List<PollsWithOption>?) {
        if (_pollWithOptionList.value.isNullOrEmpty()) {
            _pollWithOptionList.value = pollsWithOptions
        }
    }

    fun clearPollList() {
        _pollWithOptionList.value = null
    }

    fun insertPoolWithOption(
        context: Context,
        pollsTableModel: PollsTableModel,
        optionTableEntity: List<OptionTableModel>
    ) {
        PollsRepository.insertPoolWithOption(context, pollsTableModel, optionTableEntity)
    }

    fun getPoolWithOption(
        context: Context,
        isHistoryData: Boolean? = false,
        viewLifecycleOwner: LifecycleOwner
    ) {
        PollsRepository.getPoolWithOption(context, isHistoryData = isHistoryData)!!
            .observe(viewLifecycleOwner) {
                Log.d(
                    CurrentPollsFragment::class.java.name,
                    "bindAdapter View Model: $it"
                )
                updatePollList(it)
            }
    }
}