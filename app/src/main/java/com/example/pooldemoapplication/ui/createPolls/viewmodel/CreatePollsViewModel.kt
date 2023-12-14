package com.example.pooldemoapplication.ui.createPolls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pooldemoapplication.config.utils.Constant

class CreatePollsViewModel : ViewModel() {

    private val _optionCount = MutableLiveData<Int>().apply {
        value = Constant.fixedOptionCount
    }
    val optionCount: LiveData<Int> = _optionCount

    fun updateOptionCount(count: Int) {
        _optionCount.value = count
    }
}