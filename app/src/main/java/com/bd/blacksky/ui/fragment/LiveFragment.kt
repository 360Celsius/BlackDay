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
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.databinding.FragmentLiveBinding
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import com.bd.blacksky.utils.CountriesCodes
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
import java.util.*

class LiveFragment : Fragment(), KodeinAware {

    private lateinit var binding: FragmentLiveBinding

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

        val view = inflater?.inflate(R.layout.fragment_live, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUiData()
        itemsswipetorefresh.setOnRefreshListener {
            setUiData()
        }




    }


    private fun setUiData(){
        var metric: String? = null
        geoLocationViewModel.getGeoLocationFromDM().observe(viewLifecycleOwner, Observer {geoLocation ->
            if(geoLocation!=null) {
                city_name.text = geoLocation.state.toString()
                country_name.text = geoLocation.country_name.toString()
                metric = geoLocation.country_code.toString()
                time_of_day.text = getGreetingMessage()
            }
        })

        weatherViewModel.getCurrentWeatherFromDB().observe(viewLifecycleOwner, Observer { currentWeather ->
            if(currentWeather!=null && metric != null){
                if(metric.equals(CountriesCodes.UNITED_STATES_MINOR_OUTLIYING_ISLANDS.countryCode,true) || metric.equals(
                        CountriesCodes.UNITED_STATES_OF_AMERICA.countryCode,true)
                    || metric.equals(CountriesCodes.PALAU.countryCode,true) || metric.equals(CountriesCodes.BAHAMAS.countryCode,true)){
                    temp.text = currentWeather.temp?.toInt().toString() + "\u2109"
                }else{
                    temp.text = currentWeather.temp?.toInt().toString() + "\u00B0"
                }

                weather_description.text = currentWeather.main.toString()
                wind_data.text = currentWeather.wind_speed.toString() + " m/s"
                weather_animation.setAnimation( getWeatherConditionCode ( currentWeather.weather_id ) )
                weather_animation.playAnimation()
                weather_animation.loop(true)


                //val currurentWeatherEntityRandomId: Int = currentWeather.current_day_id?.toInt() ?: -1
                weatherViewModel.getAllWeeklyWeatherFromDB().observe(viewLifecycleOwner, Observer { weeklyWeather ->
                    val weeklyDayWeatherEntity1List: List<WeeklyDayWeatherEntity> = weeklyWeather.subList(1,5)

                    val layoutManager = LinearLayoutManager(context)
                    weekly_weather_view.layoutManager = layoutManager
                    weekly_weather_view.hasFixedSize()
                    weekly_weather_view.adapter = WeeklyWeatherViewAdapter(weeklyDayWeatherEntity1List,metric.toString())
                    weekly_weather_view.addItemDecoration(DividerItemDecoration(context, 0))

                    itemsswipetorefresh.isRefreshing = false
                })
            }
        })

    }

    fun getGreetingMessage():String{
        val c = Calendar.getInstance()
        val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

        return when (timeOfDay) {
            in 0..11 -> resources.getString(R.string.morning)
            in 12..15 -> resources.getString(R.string.afternoon)
            in 16..20 -> resources.getString(R.string.evening)
            in 21..23 -> resources.getString(R.string.night)
            else -> resources.getString(R.string.today)
        }
    }

    fun getWeatherConditionCode(code: Int?): String{

        return when (code){
            in 200..232 -> "thunder.json"
            in 300..321 -> "strong_showers.json"
            500 -> "light_rain.json"
            in 500..531 -> "rain.json"
            600 -> "light_snow.json"
            615 -> "rain_and_snow.json"
            in 600..622 -> "snow.json"
            in 700..781 -> "fog.json"
            800 -> "sunny.json"
            801 -> "clouds.json"
            in 800..804 -> "clouds.json"
            else -> ""
        }
    }

}