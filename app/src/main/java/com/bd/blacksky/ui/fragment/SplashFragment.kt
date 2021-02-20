package com.bd.blacksky.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bd.blacksky.R
import com.bd.blacksky.databinding.FragmentSplashBinding
import com.bd.blacksky.utils.CountriesCodes
import com.bd.blacksky.utils.Keys
import com.bd.blacksky.viewmodels.GeoLocationViewModel
import com.bd.blacksky.viewmodels.SharedViewModel
import com.bd.blacksky.viewmodels.WeatherViewModel
import com.bd.blacksky.viewmodels.factories.GeoLocationViewModelFactory
import com.bd.blacksky.viewmodels.factories.SharedViewModelFactory
import com.bd.blacksky.viewmodels.factories.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class SplashFragment : Fragment(), KodeinAware {

    private lateinit var binding: FragmentSplashBinding

    final override val kodeinContext = kcontext<Fragment>(this)
    final override val kodein: Kodein by kodein()

    private val geoLocationViewModelFactory: GeoLocationViewModelFactory by instance()
    private val weatherViewModelFactory: WeatherViewModelFactory by instance()
    private val sharedViewModelFactory: SharedViewModelFactory by instance()

    val geoLocationViewModel: GeoLocationViewModel by lazy {
        ViewModelProviders.of(this, geoLocationViewModelFactory).get(GeoLocationViewModel::class.java)
    }

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this, weatherViewModelFactory).get(WeatherViewModel::class.java)
    }

    val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(requireActivity(), sharedViewModelFactory).get(SharedViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoLocationViewModel.getGeoLocation()

        geoLocationViewModel.isEventFinishedGeoLocationViewModel.observe(viewLifecycleOwner, Observer { isEventFinishedGeoLocationViewModel ->
            if(isEventFinishedGeoLocationViewModel){
                geoLocationViewModel.getGeoLocationFromDM().observe(viewLifecycleOwner, Observer {geoLocation ->
                    if(geoLocation!=null) {
                        if(geoLocation.latitude.toString().equals(CountriesCodes.UNITED_STATES_MINOR_OUTLIYING_ISLANDS.countryCode,true) || geoLocation.latitude.toString().equals(CountriesCodes.UNITED_STATES_OF_AMERICA.countryCode,true)
                                || geoLocation.latitude.toString().equals(CountriesCodes.PALAU.countryCode,true) || geoLocation.latitude.toString().equals(CountriesCodes.BAHAMAS.countryCode,true)) {
                            weatherViewModel.getWeather(
                                geoLocation?.latitude.toString(),
                                geoLocation?.longitude.toString(),
                                Keys.apiKeyWeather(),
                                "imperial"
                            )
                        }else{
                            weatherViewModel.getWeather(
                                geoLocation?.latitude.toString(),
                                geoLocation?.longitude.toString(),
                                Keys.apiKeyWeather(),
                                "metric")
                        }
                    }
                })
            }
        })


        weatherViewModel.isEventFinishedWeatherViewModel.observe(viewLifecycleOwner, Observer { isEventFinishedWeatherViewModel ->
            if(isEventFinishedWeatherViewModel) {
                sharedViewModel.setData(true)
            }
        })

    }

}