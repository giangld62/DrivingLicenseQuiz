package com.giangle.project.ui.tips.theory_tips

import android.app.Application
import androidx.lifecycle.ViewModel
import com.giangle.project.db.AppDatabase
import com.giangle.project.db.entity.Tip

class TheoryViewModel(app: Application) : ViewModel() {
    private val appDatabase = AppDatabase.getInstance(app.applicationContext)

    suspend fun getAllTips(): List<Tip>{
        return appDatabase.getTipDao().getAllTips()
    }
}