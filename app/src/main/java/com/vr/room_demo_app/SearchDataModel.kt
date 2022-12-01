package com.vr.room_demo_app

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class SearchDataModel(
    val total_results: Long,
    val organizations: MutableList<OrganizationData>
)

@Entity
data class OrganizationData(

    @PrimaryKey(autoGenerate = true)
    var room_db_id: Int? = null,
    var ein: Int? = null,
    var strein: String? = null,
    var name: String? = null,
    var sub_name: String? = null,
    var city: String? = null,
    var state: String? = null,
    var ntee_code: String? = null,
    var raw_ntee_code: String? = null,
    var subseccd: Int? = null,
    var has_subseccd: Boolean = false,
    var have_filings: Boolean = false,
    var have_extracts: Boolean = false,
    var have_pdfs: Boolean = false,
    var score: Double? = null,
) : Serializable
