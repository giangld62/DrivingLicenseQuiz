package com.giangle.project.db.entity

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "BIENBAO")
data class TrafficSign(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int,
    @ColumnInfo(name = "NOIDUNG")
    var noiDung: String,
    @ColumnInfo(name = "LOAIBIEN")
    var loaiBien: Int,
    @Ignore
    var bienBao:Bitmap?=null
){
    constructor() : this(1,"",1)
}