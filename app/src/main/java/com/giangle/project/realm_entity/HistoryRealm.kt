package com.giangle.project.realm_entity

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class HistoryRealm : RealmObject() {
    @PrimaryKey
    var idType: String = ""
    var examNumber: Int = 0
    var listQuestions: RealmList<QuestionRealm>? = null

}