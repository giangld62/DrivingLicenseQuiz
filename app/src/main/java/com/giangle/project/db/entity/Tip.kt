package com.giangle.project.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MEO")
data class Tip(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id : Int,
    @ColumnInfo(name = "LOAIMEO")
    var loaiMeo : Int,
    @ColumnInfo(name = "NOIDUNG")
    var noiDung : String
)