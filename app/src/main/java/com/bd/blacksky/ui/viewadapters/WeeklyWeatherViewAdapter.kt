package com.bd.blacksky.ui.viewadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bd.blacksky.R
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.databinding.WeekyWeatherListItemBinding
import com.bd.blacksky.utils.CountriesCodes
import kotlinx.android.synthetic.main.fragment_live.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WeeklyWeatherViewAdapter (cryptoUsdData: List<WeeklyDayWeatherEntity>, metric: String) : RecyclerView.Adapter<WeeklyWeatherViewAdapter.ViewHolder>() {

    private var items: List<WeeklyDayWeatherEntity> = cryptoUsdData
    private var metric: String = metric

    private val loading = 0
    private val item = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && items.size > position) {


            if(metric.equals(CountriesCodes.UNITED_STATES_MINOR_OUTLIYING_ISLANDS.countryCode,true) || metric.equals(
                    CountriesCodes.UNITED_STATES_OF_AMERICA.countryCode,true)
                || metric.equals(CountriesCodes.PALAU.countryCode,true) || metric.equals(
                    CountriesCodes.BAHAMAS.countryCode,true)){

                holder.bind(dateFormatter( items[position].dt?.toString() ).toString() , "items[position].image.toString()", items[position].min?.toInt().toString() + "\u2109 " +
                        items[position].max?.toInt().toString() + "\u2109" )

            }else{

                holder.bind(dateFormatter( items[position].dt?.toString() ).toString() , "items[position].image.toString()", items[position].min?.toInt().toString() + "\u00B0 " +
                        items[position].max?.toInt().toString() + "\u00B0" )
            }
        }
    }

    fun update(items: List<WeeklyDayWeatherEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        @BindingAdapter("items")
        fun RecyclerView.bindItems(items: List<WeeklyDayWeatherEntity>) {
            val adapter = adapter as WeeklyWeatherViewAdapter
            adapter.update(items)
        }
    }

    override fun getItemViewType(position: Int) =
            item

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
            private val parent: ViewGroup,
            private val binding: WeekyWeatherListItemBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.weeky_weather_list_item,
                    parent,
                    false
            )
    ) : ViewHolder(binding.root) {

        fun bind(date: String, image: String, temp: String) {
            binding.date.text = date
            //binding.image.text = image
            binding.temp.text = temp
        }
    }

    fun dateFormatter(
        inputDateString: String?,
    ): String? {


        val ymdFormat =
            SimpleDateFormat("yyyy-MM-dd")

        val EEEddMMMyyyy =
            SimpleDateFormat("EEE MMM, dd")

        val netDate = Date((inputDateString?.toLong() ?: 0) * 1000)
        var inputDateStringNew = ymdFormat.format(netDate)


        var date: Date? = null
        var outputDateString: String? = null
        try {
            date = ymdFormat.parse(inputDateStringNew.toString())
            outputDateString = EEEddMMMyyyy.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateString
    }
}