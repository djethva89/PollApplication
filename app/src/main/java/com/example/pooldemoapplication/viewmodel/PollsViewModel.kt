package com.example.pooldemoapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.pooldemoapplication.config.room.entity.OptionTableModel
import com.example.pooldemoapplication.config.room.entity.PollsTableModel
import com.example.pooldemoapplication.config.room.entity.PollsWithOption
import com.example.pooldemoapplication.repository.PollsRepository
import com.example.pooldemoapplication.ui.currentPolls.CurrentPollsFragment

class PollsViewModel : ViewModel() {

    var pollsList: LiveData<List<PollsWithOption>?>? = null

    fun insertPoolWithOption(
        context: Context,
        pollsTableModel: PollsTableModel,
        optionTableEntity: List<OptionTableModel>
    ) {
        PollsRepository.insertPoolWithOption(context, pollsTableModel, optionTableEntity)
    }

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(value: T) {
                observer.onChanged(value)
                removeObserver(this)
            }
        })
        /*
        observeForever(object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
         */
    }

    fun getPoolWithOption(
        context: Context,
        isHistoryData: Boolean? = false
    ): LiveData<List<PollsWithOption>?> {
        pollsList = PollsRepository.getPoolWithOption(context, isHistoryData = isHistoryData)!!
        Log.d(
            CurrentPollsFragment::class.java.name,
            "bindAdapter View Model: ${pollsList?.value?.isEmpty()}"
        )
        return pollsList!!
    }
}