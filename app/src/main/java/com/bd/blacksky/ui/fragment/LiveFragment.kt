package com.bd.blacksky.ui.fragment

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.blacksky.R
import com.bd.blacksky.data.database.entities.WeeklyWeatherEntity
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import com.bd.blacksky.utils.PermissionUtils
import com.bd.blacksky.viewmodels.LiveActivityToLiveFragmentSharedViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_live.*
import java.util.*

class LiveFragment : Fragment() {

    //creates or retrieves (if already exists) an instance of MySpecialViewModel owned by MyActivity Lifecycle (keyword "activityViewModels" rather then "viewModels"
    //Note it will exist because it was created in activity already
    private val liveActivityToLiveFragmentSharedViewModel by activityViewModels<LiveActivityToLiveFragmentSharedViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_live, container, false)


        liveActivityToLiveFragmentSharedViewModel.isLocationPermissionsApproved.observe(viewLifecycleOwner, Observer { isLocationPermissionsApproved ->
            
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val weeklyWeatherEntity1List: List<WeeklyWeatherEntity> = listOf(
                WeeklyWeatherEntity(0, "Tue, Apr 16", "1", "11\u00B0\""),
                WeeklyWeatherEntity(1, "Wed, Apr 17", "2", "16\\u00B0\""),
                WeeklyWeatherEntity(1, "Thu, Apr 18", "3", "23\\u00B0\""),
                WeeklyWeatherEntity(1, "Fri, Apr 19", "4", "26\\u00B0\"")
        )

        val layoutManager = LinearLayoutManager(context)
        weekly_weather_view.layoutManager = layoutManager
        weekly_weather_view.hasFixedSize()
        weekly_weather_view.adapter = WeeklyWeatherViewAdapter(weeklyWeatherEntity1List)
        weekly_weather_view.addItemDecoration(DividerItemDecoration(context, 0))
    }





}