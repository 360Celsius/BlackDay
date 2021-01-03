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
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import com.bd.blacksky.viewmodels.LiveActivityToLiveFragmentSharedViewModel
import com.bd.blacksky.viewmodels.factories.LiveActivityToLiveFragmentSharedViewModelFactory
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

    private val liveActivityToLiveFragmentSharedViewModelFactory: LiveActivityToLiveFragmentSharedViewModelFactory by instance()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_live, container, false)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_live, container, false)
        binding.setLifecycleOwner(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val liveActivityToLiveFragmentSharedViewModel = activity?.let { ViewModelProviders.of(it, liveActivityToLiveFragmentSharedViewModelFactory).get(LiveActivityToLiveFragmentSharedViewModel::class.java) }


        liveActivityToLiveFragmentSharedViewModel?.isLocationPermissionsApproved?.observe(viewLifecycleOwner, Observer { isLocationPermissionsApproved ->
            Log.e("test", isLocationPermissionsApproved.toString())
        })

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