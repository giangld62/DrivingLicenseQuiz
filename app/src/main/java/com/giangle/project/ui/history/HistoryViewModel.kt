package com.giangle.project.ui.history

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.giangle.project.db.entity.Question
import com.giangle.project.model.ContestState
import com.giangle.project.realm_entity.ContestStateRealm
import com.giangle.project.realm_entity.HistoryRealm
import com.giangle.project.util.MapperService
import io.realm.Realm
import io.realm.RealmResults
import java.io.IOException

class HistoryViewModel(private val app: Application) : ViewModel() {
    private val mRealm = Realm.getDefaultInstance()

    fun getAllContestStates() : List<ContestState>{
        val contestStates = arrayListOf<ContestState>()
        mRealm.beginTransaction()
        val resultAllContests: RealmResults<ContestStateRealm> =
            mRealm.where(ContestStateRealm::class.java).findAll()
        if (resultAllContests.size > 10) {
            val contestStateRealm: ContestStateRealm =
                mRealm.where(ContestStateRealm::class.java).findFirst()!!
            val idToDelete = contestStateRealm.idType
            contestStateRealm.deleteFromRealm()
            val historyRealm: HistoryRealm =
                mRealm.where(HistoryRealm::class.java).equalTo("idType", idToDelete).findFirst()!!
            historyRealm.deleteFromRealm()
        }

        for( i in resultAllContests.size-1 downTo  0){
            contestStates.add(
                MapperService.mapContestStateRealmToContestState(resultAllContests[i]!!)
            )
        }
        mRealm.commitTransaction()
        return contestStates
    }

    fun getQuestionListOfContest(idType: String): List<Question>{
        mRealm.beginTransaction()
        val historyRealm: HistoryRealm =
            mRealm.where(HistoryRealm::class.java).equalTo("idType",idType)
                .findFirst()!!
        mRealm.commitTransaction()
        val questions = MapperService.mapRealmListToMutableList(historyRealm.listQuestions!!)
        addImageToArray(questions)
        return questions
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