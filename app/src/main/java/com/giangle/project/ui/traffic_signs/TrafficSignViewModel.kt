package com.giangle.project.ui.traffic_signs

import android.app.Application
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.giangle.project.db.AppDatabase
import com.giangle.project.db.entity.TrafficSign
import java.io.IOException

class TrafficSignViewModel(private val app: Application) : ViewModel() {
    private val appDatabase = AppDatabase.getInstance(app.applicationContext)

    suspend fun getAllTrafficSigns(): List<TrafficSign>{
        val trafficSings = appDatabase.getTrafficSignDao().getAllTrafficSigns()
        addImageToArray(trafficSings)
        return trafficSings
    }

    private fun addImageToArray(trafficSigns: List<TrafficSign>) {
        for (i in trafficSigns.indices) {
            try {
                val bitmap = BitmapFactory.decodeStream(
                    app.applicationContext.assets.open("${trafficSigns[i].id}.png")
                )
                trafficSigns[i].bienBao = bitmap
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}