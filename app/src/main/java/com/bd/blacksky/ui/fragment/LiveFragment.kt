package com.bd.blacksky.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bd.blacksky.R
import com.bd.blacksky.data.database.entities.WeeklyWeatherEntity
import com.bd.blacksky.ui.viewadapters.WeeklyWeatherViewAdapter
import kotlinx.android.synthetic.main.fragment_live.*

class LiveFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_live, container, false)

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
        weekly_weather_view.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
    }
}