package com.vr.room_demo_app.room_db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vr.room_demo_app.OrganizationData

@Database(entities = [(OrganizationData::class)], version = 1, exportSchema = false)
@TypeConverters(DataTypeConverter::class)
abstract class OrganizationDataBase : RoomDatabase() {
    abstract fun OrganizationDao(): OrganizationDao
}