package com.vr.room_demo_app.room_db

import androidx.room.*
import com.vr.room_demo_app.OrganizationData

@Dao
interface OrganizationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrganization(cakeOrder: List<OrganizationData>)

    @Update
    fun updateOrganization(cakeOrder: OrganizationData)

    @Query("UPDATE OrganizationData SET has_subseccd=:hasSubseccd WHERE room_db_id = :id")
    fun updateHasSubseccd(hasSubseccd: Boolean, id: Int)

    @Query("SELECT * FROM OrganizationData")
    fun getAll(): List<OrganizationData>

    @Query("DELETE FROM OrganizationData")
    fun deleteAll()

    @Query("DELETE FROM OrganizationData WHERE room_db_id = :id")
    fun deleteOrganization(id: Int)

    @Delete
    fun deleteSingleData(data:OrganizationData)


    @Query("SELECT COUNT(room_db_id) FROM OrganizationData")
    fun getOrganizationCount(): Int

}