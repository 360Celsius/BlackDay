package com.bd.blacksky.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.blacksky.R
import com.bd.blacksky.data.database.entities.WeeklyWeatherEntity
import com.bd.blacksky.databinding.FragmentLiveBinding
import com.bd.blacksky.databinding.FragmentSplashBinding
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import com.bd.blacksky.viewmodels.GeoLocationViewModel
import com.bd.blacksky.viewmodels.WeatherViewModel
import com.bd.blacksky.viewmodels.factories.GeoLocationViewModelFactory
import com.bd.blacksky.viewmodels.factories.WeatherViewModelFactory
import kotlinx.android.synthetic.main.fragment_live.*
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


    val geoLocationViewModel: GeoLocationViewModel by lazy {
        ViewModelProviders.of(this, geoLocationViewModelFactory).get(GeoLocationViewModel::class.java)
    }

    val weatherViewModel: WeatherViewModel by lazy {
        ViewModelProviders.of(this, weatherViewModelFactory).get(WeatherViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_splash, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        geoLocationViewModel.getGeoLocation()


        geoLocationViewModel.getGeoLocationFromDM().observe(viewLifecycleOwner, Observer {geoLocation ->
            if(geoLocation!=null) {
                weatherViewModel.getWeather(geoLocation.latitude.toString(),geoLocation.longitude.toString(),"aa2df23d347d91a01f286584e35f2b7e")
                Log.e("test", "Splas" + geoLocation.country_name.toString())
            }
        })


    }





}