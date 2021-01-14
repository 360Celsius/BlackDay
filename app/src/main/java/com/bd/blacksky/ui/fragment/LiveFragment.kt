package com.bd.blacksky.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.blacksky.R
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.databinding.FragmentLiveBinding
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import com.bd.blacksky.viewmodels.GeoLocationViewModel
import com.bd.blacksky.viewmodels.factories.GeoLocationViewModelFactory
import kotlinx.android.synthetic.main.fragment_live.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class LiveFragment : Fragment(), KodeinAware {

    private lateinit var binding: FragmentLiveBinding

    final override val kodeinContext = kcontext<Fragment>(this)
    final override val kodein: Kodein by kodein()

    private val geoLocationViewModelFactory: GeoLocationViewModelFactory by instance()

    val geoLocationViewModel: GeoLocationViewModel by lazy {
        ViewModelProviders.of(this, geoLocationViewModelFactory).get(GeoLocationViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_live, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        geoLocationViewModel.getGeoLocation()
//
//        geoLocationViewModel.getGeoLocationFromDM().observe(viewLifecycleOwner, Observer {geoLocation ->
//            if(geoLocation!=null) {
//                Log.e("test", geoLocation.country_name.toString())
//            }
//        })

        val weeklyDayWeatherEntity1List: List<WeeklyDayWeatherEntity> = listOf(
                WeeklyDayWeatherEntity(0, 0,1610269200,283.19, 293.31, "clear sky","Clear"),
                WeeklyDayWeatherEntity(1, 1,1610269200,283.19, 293.31, "clear sky","Clear"),
                WeeklyDayWeatherEntity(1, 2,1610269200,283.19, 293.31, "clear sky","Clear"),
                WeeklyDayWeatherEntity(1, 3,1161026920, 293.31, 293.31,"clear sky","Clear")
        )

        val layoutManager = LinearLayoutManager(context)
        weekly_weather_view.layoutManager = layoutManager
        weekly_weather_view.hasFixedSize()
        weekly_weather_view.adapter = WeeklyWeatherViewAdapter(weeklyDayWeatherEntity1List)
        weekly_weather_view.addItemDecoration(DividerItemDecoration(context, 0))
    }





}