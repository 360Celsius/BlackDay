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

class WeeklyWeatherViewAdapter (cryptoUsdData: List<WeeklyDayWeatherEntity>) : RecyclerView.Adapter<WeeklyWeatherViewAdapter.ViewHolder>() {

    private var items: List<WeeklyDayWeatherEntity> = cryptoUsdData

    private val loading = 0
    private val item = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(parent)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && items.size > position) {
           // holder.bind(items[position].date.toString(), items[position].image.toString(), items[position].temp.toString())
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
            //binding.date.text = date
            //binding.image.text = image
            //binding.temp.text = temp
        }
    }
}