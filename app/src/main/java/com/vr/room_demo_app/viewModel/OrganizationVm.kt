package com.vr.room_demo_app.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vr.room_demo_app.AppController
import com.vr.room_demo_app.OrganizationData
import com.vr.room_demo_app.SearchDataModel
import com.vr.room_demo_app.network_calls.APIClient
import com.vr.room_demo_app.network_calls.SearchApi
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizationVm(application: Application) : AndroidViewModel(application) {
    val organizationData: MutableLiveData<List<OrganizationData>> = MutableLiveData()

    var search_key: MutableLiveData<String> = MutableLiveData()

    fun getSearchKeyDbObserver(): MutableLiveData<String> {
        return search_key
    }

    fun checkOrganizationData() {
        viewModelScope.launch {
            val organizationList = AppController.instance?.roomDbHelper?.OrganizationDao()?.getAll()
            if (organizationList.isNullOrEmpty()) {
                fetchSearchAPIData()
            } else {
                organizationData.postValue(organizationList)
            }
        }
    }

    fun fetchSearchAPIData() {
        val client = APIClient().getInstance().create(SearchApi::class.java)
        val call = client.searchData()

        call.enqueue(object : Callback<SearchDataModel> {
            override fun onResponse(
                call: Call<SearchDataModel>,
                response: Response<SearchDataModel>
            ) {
                try {
                    val apiResponse = response.body() as SearchDataModel
                    viewModelScope.launch {
                        AppController.instance?.roomDbHelper?.OrganizationDao()?.deleteAll()
                        AppController.instance?.roomDbHelper?.OrganizationDao()
                            ?.addOrganization(apiResponse.organizations)

                        organizationData.postValue(
                            AppController.instance?.roomDbHelper?.OrganizationDao()
                                ?.getAll()
                        )
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<SearchDataModel>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

    fun deleteOrganization(itemId: Int) {
        viewModelScope.launch {
            AppController.instance?.roomDbHelper?.OrganizationDao()?.deleteOrganization(itemId)
        }
    }

}