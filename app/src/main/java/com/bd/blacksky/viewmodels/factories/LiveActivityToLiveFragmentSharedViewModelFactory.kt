package com.bd.blacksky.viewmodels.factories

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bd.blacksky.viewmodels.LiveActivityToLiveFragmentSharedViewModel

class LiveActivityToLiveFragmentSharedViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LiveActivityToLiveFragmentSharedViewModel() as T
    }
}