package com.bd.blacksky.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LiveActivityToLiveFragmentSharedViewModel (): ViewModel() {

    val isLocationPermissionsApproved : MutableLiveData<Boolean> = MutableLiveData<Boolean>()

}

