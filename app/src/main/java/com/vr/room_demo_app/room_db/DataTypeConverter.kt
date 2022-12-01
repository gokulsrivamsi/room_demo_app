package com.vr.room_demo_app.room_db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vr.room_demo_app.OrganizationData

class DataTypeConverter {
    @TypeConverter
    fun birthdayItemsList(json: String?): List<OrganizationData>? {
        val type = object : TypeToken<List<OrganizationData>>() {}.type
        return if (json != null) {
            Gson().fromJson(json, type)
        } else {
            null
        }
    }
}