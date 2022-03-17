package com.giangle.project.util

import com.giangle.project.db.entity.Question
import com.giangle.project.model.ContestState
import com.giangle.project.model.ContestStatus
import com.giangle.project.realm_entity.ContestStateRealm
import com.giangle.project.realm_entity.ContestStatusRealm
import com.giangle.project.realm_entity.QuestionRealm
import io.realm.RealmList

object MapperService {
    fun mapContestStateRealmToContestState(contestStateRealm: ContestStateRealm): ContestState {
        val contestState = ContestState()
        contestState.typeOfContest = contestStateRealm.typeOfContest
        contestState.idType = contestStateRealm.idType
        contestState.examNumber = contestStateRealm.examNumber
        contestState.isPassed = contestStateRealm.isPassed
        return contestState
    }

    fun mapRealmListToMutableList(realmListQuestions: RealmList<QuestionRealm>): MutableList<Question> {
        val questions = mutableListOf<Question>()
        for (questionRealm in realmListQuestions) {
            questions.add(mapQuestionRealmToQuestion(questionRealm))
        }
        return questions
    }

    fun mapQuestionToQuestionRealm(question: Question, yourAnswer: String?): QuestionRealm {
        val res = QuestionRealm()
        res.id = question.id
        res.cauhoi = question.cauHoi
        res.anh = question.anh
        res.a = question.a
        res.b = question.b
        res.c = question.c
        res.d = question.d
        res.cauDiemLiet = question.cauDiemLiet
        res.dapAnDung = question.dapAnDung
        res.answers = yourAnswer
        return res
    }

    private fun mapQuestionRealmToQuestion(questionRealm: QuestionRealm): Question {
        val question = Question()
        question.id = questionRealm.id
        question.anh = questionRealm.anh
        question.cauHoi = questionRealm.cauhoi
        question.a = questionRealm.a
        question.b = questionRealm.b
        question.c = questionRealm.c
        question.d = questionRealm.d
        question.id = questionRealm.id
        question.dapAnDung = questionRealm.dapAnDung
        question.cauDiemLiet = questionRealm.cauDiemLiet
        question.cauTraLoi = questionRealm.answers
        return question
    }

    fun convertListStringToBooleanMatrix(answers: MutableList<String>): Array<BooleanArray> {
        val answersConverted = Array(35) { BooleanArray(4) }
        for (i in answers.indices) {
            answersConverted[i][0] = false
            answersConverted[i][1] = false
            answersConverted[i][2] = false
            answersConverted[i][3] = false
            if (answers[i].isNotEmpty())
                answersConverted[i][(answers[i].toInt() - 1)] = true
        }
        return answersConverted
    }

    fun mapContestStatusRealmToContestStatus(contestStatusRealm: ContestStatusRealm): ContestStatus {
        val contestStatus = ContestStatus()
        contestStatus.idType = contestStatusRealm.idType
        contestStatus.typeOfContest = contestStatusRealm.typeOfContest
        contestStatus.status = contestStatusRealm.status
        return contestStatus
    }

}