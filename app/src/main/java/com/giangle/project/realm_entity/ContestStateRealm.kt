package com.giangle.project.realm_entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContestStateRealm : RealmObject() {
    @PrimaryKey
    var idType: String = ""
    var examNumber: Int = 0
    var typeOfContest: String = ""
    var isPassed = false
}