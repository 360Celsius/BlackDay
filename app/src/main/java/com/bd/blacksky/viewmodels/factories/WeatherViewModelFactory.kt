package com.bd.blacksky.viewmodels.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.repositories.WeatherRepository
import com.bd.blacksky.viewmodels.GeoLocationViewModel
import com.bd.blacksky.viewmodels.WeatherViewModel

class WeatherViewModelFactory(
        private val weatherRepository: WeatherRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WeatherViewModel(weatherRepository) as T
    }
}