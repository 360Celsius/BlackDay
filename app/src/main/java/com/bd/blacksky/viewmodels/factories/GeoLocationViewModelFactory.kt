package com.bd.blacksky.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.viewmodels.GeoLocationViewModel

class GeoLocationViewModelFactory(
        private val geoLocationRepository: GeoLocationRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GeoLocationViewModel(geoLocationRepository) as T
    }
}