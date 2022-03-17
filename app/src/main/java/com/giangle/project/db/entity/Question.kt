package com.giangle.project.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "CAUHOI")
data class Question(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id :Int,
    @ColumnInfo(name = "ANH")
    var anh : Int,
    @ColumnInfo(name = "CAUHOI")
    var cauHoi : String,
    @ColumnInfo(name = "A")
    var a : String? = null,
    @ColumnInfo(name = "B")
    var b : String? = null,
    @ColumnInfo(name = "C")
    var c : String? = null,
    @ColumnInfo(name = "D")
    var d : String? = null,
    @ColumnInfo(name = "DAPAN")
    var dapAnDung : String,
    @ColumnInfo(name = "LOAICAUHOI")
    var loaiCauHoi: Int,
    @ColumnInfo(name = "CAUDIEMLIET")
    var cauDiemLiet: Int? = 0,
    @ColumnInfo(name = "LOAIBANG")
    var loaiBanng: String? = null,
    @Ignore
    var bienBao : Bitmap? = null,
    @Ignore
    var cauTraLoi : String? = null
){
    constructor() : this(0,0,"","","","","","",0,0)
}