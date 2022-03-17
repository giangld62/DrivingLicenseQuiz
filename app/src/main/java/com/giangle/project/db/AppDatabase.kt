package com.giangle.project.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.giangle.project.db.dao.TipDAO
import com.giangle.project.db.dao.QuestionDAO
import com.giangle.project.db.dao.TrafficSignDAO
import com.giangle.project.db.entity.Question
import com.giangle.project.db.entity.Tip
import com.giangle.project.db.entity.TrafficSign

@Database(entities = [Tip::class, TrafficSign::class, Question::class],version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun getTipDao(): TipDAO
    abstract fun getTrafficSignDao(): TrafficSignDAO
    abstract fun getQuestionDao(): QuestionDAO

    companion object{
        private var INSTANCE: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context,AppDatabase::class.java,"banglaixe.db")
                    .fallbackToDestructiveMigration()
                    .createFromAsset("BANGLAIXE.db")
                    .build()
            }
            return INSTANCE!!
        }
    }
}