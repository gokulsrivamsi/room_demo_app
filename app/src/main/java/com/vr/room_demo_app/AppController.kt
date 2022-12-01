package com.vr.room_demo_app

import android.app.Application
import androidx.room.Room
import com.vr.room_demo_app.room_db.OrganizationDataBase

class AppController : Application() {
    var roomDbHelper: OrganizationDataBase? = null
    var roomDb_name = "Organization_Db"
    override fun onCreate() {
        super.onCreate()
        if (instance == null) {
            instance = this
        }
        if (roomDbHelper == null) {
            roomDbHelper = Room.databaseBuilder(
                this,
                OrganizationDataBase::class.java,
                roomDb_name
            ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }

    companion object {
        var instance: AppController? = null
    }
}