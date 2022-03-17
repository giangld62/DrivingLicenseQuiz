package com.giangle.project.realm_entity

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class QuestionRealm : RealmObject() {
    @PrimaryKey
    var id = 0
    var anh = 0
    var cauhoi: String = ""
    var a: String? = null
    var b: String? = null
    var c: String? = null
    var d: String? = null
    var cauDiemLiet: Int? = 0
    var dapAnDung: String = ""
    var answers: String? = null
}