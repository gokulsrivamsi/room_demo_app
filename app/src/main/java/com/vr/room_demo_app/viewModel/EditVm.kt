package com.vr.room_demo_app.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vr.room_demo_app.AppController
import com.vr.room_demo_app.EditDataActivity
import com.vr.room_demo_app.OrganizationData
import kotlinx.coroutines.launch

class EditVm : ViewModel() {
    val organizationData: MutableLiveData<OrganizationData> = MutableLiveData()

    fun getDetails(data: OrganizationData) {
        viewModelScope.launch {
            organizationData.postValue(
                data
            )
        }
    }

    fun updateData(view: View) {
        viewModelScope.launch {
            organizationData.value?.let {
                AppController.instance?.roomDbHelper?.OrganizationDao()?.updateOrganization(
                    it
                )
            }

            (view.context as EditDataActivity).onBackPressed()
        }
    }
}