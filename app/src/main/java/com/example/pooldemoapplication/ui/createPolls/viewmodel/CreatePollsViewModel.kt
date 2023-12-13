package com.example.pooldemoapplication.ui.createPolls.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreatePollsViewModel : ViewModel() {

    private val _text = MutableLiveData<Int>().apply {
        value = 0
    }
    val text: LiveData<Int> = _text
}