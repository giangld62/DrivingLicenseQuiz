package com.giangle.project.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.giangle.project.db.entity.Tip


@Dao
interface TipDAO {
    @Query("SELECT * FROM MEO")
    suspend fun getAllTips(): MutableList<Tip>

}