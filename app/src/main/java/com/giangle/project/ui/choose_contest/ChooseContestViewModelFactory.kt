package com.giangle.project.ui.choose_contest

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChooseContestViewModelFactory(private val typeOfContest: String,private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChooseContestViewModel::class.java))
            return ChooseContestViewModel(typeOfContest,app) as T
        else
            throw IllegalArgumentException("Unknown view model")
    }
}