package com.giangle.project.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.giangle.project.db.entity.Question


@Dao
interface QuestionDAO {
    @Query("SELECT * FROM CAUHOI WHERE LOAIBANG LIKE 'A1%' ORDER BY ID")
    suspend fun getAllQuestionA1(): List<Question>

    @Query("SELECT * FROM CAUHOI WHERE LOAIBANG LIKE '%A2' ORDER BY ID")
    suspend fun getAllQuestionA2(): List<Question>

    @Query("SELECT * FROM CAUHOI")
    suspend fun getAllQuestion(): List<Question>
}