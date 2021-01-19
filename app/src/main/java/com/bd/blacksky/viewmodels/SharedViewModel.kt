package com.bd.blacksky.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel (

): ViewModel(){
    val data = MutableLiveData<Boolean>()

    fun setData(isFinished: Boolean) {
        data.value = isFinished
    }
}