package com.giangle.project.realm_entity


import com.giangle.project.util.Const.UNTESTED
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ContestStatusRealm : RealmObject() {
    @PrimaryKey
    var idType: String = ""
    var typeOfContest: String = ""
    var status = UNTESTED
}