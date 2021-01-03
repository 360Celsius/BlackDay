package com.bd.blacksky.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class LiveActivityToLiveFragmentSharedViewModel (application: Application): AndroidViewModel(application) {

    val isLocationPermissionsApproved = MutableLiveData<Boolean>() //typically private and exposed through method

    fun setIsLocationPermissionsApproved(value: Boolean) {
        isLocationPermissionsApproved.value = value //triggers observers
    }

}