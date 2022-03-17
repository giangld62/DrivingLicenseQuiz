package com.giangle.project.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.giangle.project.ui.history.HistoryViewModel
import com.giangle.project.ui.main.MainViewModel
import com.giangle.project.ui.practice_questions.PracticeQuestionsViewModel
import com.giangle.project.ui.tips.theory_tips.TheoryViewModel
import com.giangle.project.ui.traffic_signs.TrafficSignViewModel

class ViewModelFactory(private val app: Application?) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TrafficSignViewModel::class.java)){
            return TrafficSignViewModel(app!!) as T
        }
        else if(modelClass.isAssignableFrom(TheoryViewModel::class.java)){
            return TheoryViewModel(app!!) as T
        }
        else if(modelClass.isAssignableFrom(PracticeQuestionsViewModel::class.java)){
            return PracticeQuestionsViewModel(app!!) as T
        }
        else if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel() as T
        }
        else if(modelClass.isAssignableFrom(HistoryViewModel::class.java))
            return HistoryViewModel(app!!) as T
        else throw IllegalArgumentException("Unknown view model")
    }
}