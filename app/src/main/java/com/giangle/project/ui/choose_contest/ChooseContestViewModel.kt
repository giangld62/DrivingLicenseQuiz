package com.giangle.project.ui.choose_contest

import android.app.Application
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.giangle.project.db.AppDatabase
import com.giangle.project.db.entity.Question
import com.giangle.project.model.ContestStatus
import com.giangle.project.realm_entity.ContestStatusRealm
import com.giangle.project.util.Const.A1
import com.giangle.project.util.Const.A2
import com.giangle.project.util.Const.B1
import com.giangle.project.util.Const.B2
import com.giangle.project.util.MapperService
import io.realm.Realm
import io.realm.RealmResults
import java.io.IOException
import java.util.*
import java.util.stream.IntStream

class ChooseContestViewModel(private val typeOfContest: String, private val app: Application) : ViewModel() {
    private val contestStatuses = arrayListOf<ContestStatus>()
    private val _listOfContestStatus = MutableLiveData<List<ContestStatus>>()
    val listOfContestStatus: LiveData<List<ContestStatus>>
        get() = _listOfContestStatus
    private  val appDatabase = AppDatabase.getInstance(app.applicationContext)

    init {
        when (typeOfContest) {
            A1 ->
                for (i in 0..7)
                    contestStatuses.add(ContestStatus("${i + 1}_A1", A1, "UNTESTED"))
            A2 ->
                for (i in 0..17)
                    contestStatuses.add(ContestStatus("${i + 1}_A2", A2, "UNTESTED"))
            B1 ->
                for (i in 0..19)
                    contestStatuses.add(ContestStatus("${i + 1}_B1", B1, "UNTESTED"))
            else ->
                for (i in 0..17)
                    contestStatuses.add(ContestStatus("${i + 1}_B2", B2, "UNTESTED"))
        }
        _listOfContestStatus.postValue(contestStatuses)
    }

    private suspend fun getAllQuestion(): List<Question>{
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
        return questions
    }

    suspend fun getQuestionsForContest(position: Int): List<Question>{
        val questions = getAllQuestion()
        return when(position){
            -1 -> getListCauHoiDiemLiet(questions)
            -2 -> getQuestionListRandom(typeOfContest,questions)
            else -> setQuestionForEachContest(typeOfContest,questions)[position]
        }
    }

    fun updateContestStatuses(){
        val resultList = readAllStatusContests(typeOfContest)
        when (typeOfContest) {
            A1 -> {
                for (i in 0..7) {
                    for (j in 0 until resultList.size) {
                        if (contestStatuses[i].idType == resultList[j].idType) {
                            contestStatuses[i].status = resultList[j].status
                        }
                    }
                }
            }
            A2 -> {
                for (i in 0..17) {
                    for (j in 0 until resultList.size) {
                        if (contestStatuses[i].idType == resultList[j].idType) {
                            contestStatuses[i].status = resultList[j].status
                        }
                    }
                }
            }
            B1 -> {
                for (i in 0..19) {
                    for (j in 0 until resultList.size) {
                        if (contestStatuses[i].idType == resultList[j].idType) {
                            contestStatuses[i].status = resultList[j].status
                        }
                    }
                }
            }
            else -> {
                for (i in 0..17) {
                    for (j in 0 until resultList.size) {
                        if (contestStatuses[i].idType == resultList[j].idType) {
                            contestStatuses[i].status = resultList[j].status
                        }
                    }
                }
            }
        }
        _listOfContestStatus.postValue(contestStatuses)
    }

    private fun readAllStatusContests(typeOfContest: String): MutableList<ContestStatus> {
        val contestStatuses = mutableListOf<ContestStatus>()
        val mRealm = Realm.getDefaultInstance()
        mRealm!!.beginTransaction()
        val contestStatusRealms: RealmResults<ContestStatusRealm> =
            mRealm.where(ContestStatusRealm::class.java).findAll()
        Log.d("contestStatusRealms", contestStatusRealms.size.toString())
        for (contestStatusRealm in contestStatusRealms) {
            val contestStatus =
                MapperService.mapContestStatusRealmToContestStatus(contestStatusRealm)
            if (contestStatus.typeOfContest == typeOfContest) {
                contestStatuses.add(contestStatus)
            }
        }
        Log.d("contestStatuses", contestStatuses.size.toString())
        mRealm.commitTransaction()
        mRealm.close()
        return contestStatuses
    }

    private fun setQuestionForEachContest(
        typeOfContest: String,
        questions: List<Question>
    ): List<List<Question>> {
        return when (typeOfContest) {
            A1 -> {
                setQuestionForEachContestA1(questions)
            }
            A2 -> {
                setQuestionForEachContestA2(questions)
            }
            B1 -> {
                setQuestionForEachContestB1(questions)
            }
            else -> {
                setQuestionForEachContestB2(questions)
            }
        }
    }

    private fun setQuestionForEachContestA1(questions: List<Question>): MutableList<MutableList<Question>> {
        val questionsPerContest: MutableList<MutableList<Question>> = mutableListOf()
        for (i in 0..7) {
            val ques = mutableListOf<Question>()
            if (i < 4) {
                for (j in 0..7) {
                    val temp = i * 8
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp])
                }
                for (j in 0..2) {
                    val temp = i * 3
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp])
                }
                for (j in 0..6) {
                    val temp = i * 7
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..6) {
                    val temp = i * 7
                    ques.add(getListCauHoiSaHinh(questions)[j + temp])
                }
            } else {
                for (j in 0..8) {
                    val temp = i * 9
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp])
                }
                for (j in 0..1) {
                    val temp = i * 2
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp + 4])
                }
                for (j in 0..6) {
                    val temp = i * 7
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..6) {
                    var temp = i * 7
                    if (temp < getListCauHoiSaHinh(questions).size) {
                        ques.add(getListCauHoiSaHinh(questions)[j + temp])
                    } else {
                        temp = i
                        ques.add(getListCauHoiSaHinh(questions)[j + temp])
                    }
                }
            }
            questionsPerContest.add(ques)
        }
        return questionsPerContest
    }

    private fun setQuestionForEachContestA2(questions: List<Question>): MutableList<MutableList<Question>> {
        val questionsPerContest: MutableList<MutableList<Question>> = mutableListOf()
        for (i in 0..17) {
            val ques = mutableListOf<Question>()
            if (i < 14) {
                for (j in 0..6) {
                    val temp = i * 7
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp])
                }
                for (j in 0..2) {
                    val temp = i * 3
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp])
                }
                for (j in 0..9) {
                    val temp = i * 10
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..4) {
                    val temp = i * 5
                    ques.add(getListCauHoiSaHinh(questions)[j + temp])
                }
            } else {
                for (j in 0..7) {
                    val temp = i * 8
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp - 14])
                }
                for (j in 0..1) {
                    val temp = i * 2
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp + 14])
                }
                for (j in 0..9) {
                    val temp = i * 10
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..4) {
                    var temp = i * 5
                    if (temp < getListCauHoiSaHinh(questions).size) {
                        ques.add(getListCauHoiSaHinh(questions)[j + temp])
                    } else {
                        temp = i
                        ques.add(getListCauHoiSaHinh(questions)[j + temp])
                    }
                }
            }
            questionsPerContest.add(ques)
        }
        return questionsPerContest
    }

    private fun setQuestionForEachContestB1(questions: List<Question>): List<MutableList<Question>> {
        val questionsPerContest: MutableList<MutableList<Question>> = mutableListOf()
        for (i in 0..19) {
            val ques = mutableListOf<Question>()
            if (i < 14) {
                for (j in 0..11) {
                    val temp = i * 12
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp])
                }
                for (j in 0..2) {
                    val temp = i * 3
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp])
                }
                for (j in 0..8) {
                    val temp = i * 9
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..5) {
                    val temp = i * 6
                    ques.add(getListCauHoiSaHinh(questions)[j + temp])
                }
            } else {
                if(i in 14..18){
                    for (j in 0..12) {
                        val temp = i * 13
                        ques.add(getListCauHoiBinhThuong(questions)[j + temp - 14])
                    }
                }
                else{
                    for(j in 232..242){
                        ques.add(getListCauHoiBinhThuong(questions)[j])
                    }
                    ques.add(getListCauHoiBienBao(questions)[180])
                    ques.add(getListCauHoiBienBao(questions)[181])
                }
                for (j in 0..2) {
                    val temp = i * 3
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp])
                }
                for (j in 0..8) {
                    val temp = i * 9
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..4) {
                    val temp = i * 5
                    ques.add(getListCauHoiSaHinh(questions)[j + temp + 14])
                }
            }
            questionsPerContest.add(ques)
        }
        return questionsPerContest
    }

    private fun setQuestionForEachContestB2(questions: List<Question>): List<MutableList<Question>> {
        val questionsPerContest: MutableList<MutableList<Question>> = mutableListOf()
        for (i in 0..17) {
            val ques = mutableListOf<Question>()
            if (i < 12) {
                for (j in 0..15) {
                    val temp = i * 16
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp])
                }
                for (j in 0..2) {
                    val temp = i * 3
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp])
                }
                for (j in 0..9) {
                    val temp = i * 10
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..5) {
                    val temp = i * 6
                    ques.add(getListCauHoiSaHinh(questions)[j + temp])
                }
            } else {
                for (j in 0..13) {
                    val temp = if (i < 15) {
                        i * 14
                    } else {
                        i
                    }
                    ques.add(getListCauHoiBinhThuong(questions)[j + temp + 24])
                }
                for (j in 0..3) {
                    val temp = i * 4
                    ques.add(getListCauHoiDiemLiet(questions)[j + temp - 12])
                }
                for (j in 0..9) {
                    val temp = i * 10
                    ques.add(getListCauHoiBienBao(questions)[j + temp])
                }
                for (j in 0..6) {
                    val temp = i * 7
                    ques.add(getListCauHoiSaHinh(questions)[j + temp - 12])
                }
            }
            questionsPerContest.add(ques)
        }
        return questionsPerContest
    }

    private fun getQuestionListRandom(
        typeOfContest: String,
        questions: List<Question>
    ): List<Question> {
        val ques = mutableListOf<Question>()
        var array1 = intArrayOf()
        var array2 = intArrayOf()
        var array3 = intArrayOf()
        var array4 = intArrayOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            array1 = shuffleArray(
                IntStream.rangeClosed(0, getListCauHoiBinhThuong(questions).size-1).toArray()
            )
            array2 = shuffleArray(
                IntStream.rangeClosed(0, getListCauHoiDiemLiet(questions).size-1).toArray()
            )
            array3 = shuffleArray(
                IntStream.rangeClosed(0, getListCauHoiBienBao(questions).size-1).toArray()
            )
            array4 = shuffleArray(
                IntStream.rangeClosed(0, getListCauHoiSaHinh(questions).size-1).toArray()
            )
        }
        when (typeOfContest) {
            A1 -> {
                for (i in 0..9){
                    ques.add(getListCauHoiBinhThuong(questions)[array1[i]])
                }
                ques.add(getListCauHoiDiemLiet(questions)[array2[0]])
                for (i in 0..6){
                    ques.add(getListCauHoiBienBao(questions)[array3[i]])
                }
                for (i in 0..6) {
                    ques.add(getListCauHoiSaHinh(questions)[array4[i]])
                }
            }

            A2 ->{
                for (i in 0..8)
                    ques.add(getListCauHoiBinhThuong(questions)[array1[i]])
                for(i in 0..1)
                    ques.add(getListCauHoiDiemLiet(questions)[array2[i]])
                for (i in 0..6)
                    ques.add(getListCauHoiBienBao(questions)[array3[i]])
                for (i in 0..6)
                    ques.add(getListCauHoiSaHinh(questions)[array4[i]])
            }

            B1 ->{
                for (i in 0..9)
                    ques.add(getListCauHoiBinhThuong(questions)[array1[i]])
                for(i in 0..1)
                    ques.add(getListCauHoiDiemLiet(questions)[array2[i]])
                for (i in 0..8)
                    ques.add(getListCauHoiBienBao(questions)[array3[i]])
                for (i in 0..8)
                    ques.add(getListCauHoiSaHinh(questions)[array4[i]])
            }

            B2 ->{
                for (i in 0..11)
                    ques.add(getListCauHoiBinhThuong(questions)[array1[i]])
                for(i in 0..2)
                    ques.add(getListCauHoiDiemLiet(questions)[array2[i]])
                for (i in 0..9)
                    ques.add(getListCauHoiBienBao(questions)[array3[i]])
                for (i in 0..9)
                    ques.add(getListCauHoiSaHinh(questions)[array4[i]])
            }

        }
        return ques
    }

    private fun getListCauHoiBinhThuong(questions: List<Question>): List<Question> {
        val listCauHoiBinhThuong = mutableListOf<Question>()
        for (item in questions) {
            if (item.loaiCauHoi != 6 && item.loaiCauHoi != 7 && item.cauDiemLiet != 1) {
                listCauHoiBinhThuong.add(item)
            }
        }
        return listCauHoiBinhThuong
    }

    private fun getListCauHoiBienBao(questions: List<Question>): List<Question> {
        val listCauHoiBienBao = mutableListOf<Question>()
        for (item in questions) {
            if (item.loaiCauHoi == 6) {
                listCauHoiBienBao.add(item)
            }
        }
        return listCauHoiBienBao
    }

    private fun getListCauHoiSaHinh(questions: List<Question>): List<Question> {
        val listCauHoiSaHinh = mutableListOf<Question>()
        for (item in questions) {
            if (item.loaiCauHoi == 7) {
                listCauHoiSaHinh.add(item)
            }
        }
        return listCauHoiSaHinh
    }

    private fun getListCauHoiDiemLiet(questions: List<Question>): List<Question> {
        val listCauHoiDiemLiet = mutableListOf<Question>()
        for (item in questions) {
            if (item.cauDiemLiet == 1) {
                listCauHoiDiemLiet.add(item)
            }
        }
        return listCauHoiDiemLiet
    }

    private fun shuffleArray(array: IntArray): IntArray {
        var index: Int
        var temp: Int
        val random = Random()
        for (i in array.size - 1 downTo 1) {
            index = random.nextInt(i + 1)
            temp = array[index]
            array[index] = array[i]
            array[i] = temp
        }
        return array
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