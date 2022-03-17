package com.giangle.project.ui.practice_questions

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.giangle.project.db.AppDatabase
import com.giangle.project.db.entity.Question
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import java.io.IOException

class PracticeQuestionsViewModel(private val app: Application) : ViewModel() {
    private val appDatabase = AppDatabase.getInstance(app.applicationContext)

    suspend fun getListOfQuestionList(typeOfContest: String): List<List<Question>> {
        val questions = when(typeOfContest){
            A1 ->{
                appDatabase.getQuestionDao().getAllQuestionA1()
            }
            A2 ->{
                appDatabase.getQuestionDao().getAllQuestionA2()
            }
            else ->{
                appDatabase.getQuestionDao().getAllQuestion()
            }
        }
        addImageToArray(questions)
        return createQuestionListPerPage(typeOfContest,questions)
    }


    private fun createQuestionListPerPage(typeOfContest: String,questions: List<Question>): List<List<Question>> {
        val list1: MutableList<MutableList<Question>> = arrayListOf()
        when (typeOfContest) {
            A1 -> {
                for (i in 0..7) {
                    val list2 = mutableListOf<Question>()
                    for (j in i * 25 until (i + 1) * 25) {
                        list2.add(questions[j])
                    }
                    list1.add(list2)
                }
            }

            A2 -> {
                for (i in 0..17) {
                    val list2 = mutableListOf<Question>()
                    for (j in i * 25 until (i + 1) * 25) {
                        list2.add(questions[j])
                    }
                    list1.add(list2)
                }
            }

            B1 -> {
                for (i in 0..19) {
                    val list2 = mutableListOf<Question>()
                    for (j in i * 30 until (i + 1) * 30) {
                        list2.add(questions[j])
                    }
                    list1.add(list2)
                }
            }
        }
        return list1
    }


    private fun addImageToArray(questions: List<Question>) {
        for (i in questions.indices) {
            try {
                val bitmap = BitmapFactory.decodeStream(
                    app.applicationContext.assets.open("cau${questions[i].id}.png")
                )
                questions[i].bienBao = bitmap
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}