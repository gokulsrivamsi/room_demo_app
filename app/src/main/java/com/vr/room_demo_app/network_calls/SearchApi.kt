package com.vr.room_demo_app.network_calls

import com.vr.room_demo_app.SearchDataModel
import retrofit2.Call
import retrofit2.http.GET

interface SearchApi {

    @GET("v2/search.json")
    fun searchData(): Call<SearchDataModel>


}