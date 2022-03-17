package com.giangle.project.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _textViewContent = MutableLiveData<String>()
    val textViewContent: LiveData<String>
        get() = _textViewContent


    fun updateTextView(text: String){
        _textViewContent.postValue(text)
    }
}