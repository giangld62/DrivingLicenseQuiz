package com.giangle.project.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.giangle.project.db.entity.TrafficSign

@Dao
interface TrafficSignDAO {
    @Query("select * from BIENBAO")
    suspend fun getAllTrafficSigns(): List<TrafficSign>
}