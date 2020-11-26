package com.kfouri.climaap.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfouri.climaap.GlideApp
import com.kfouri.climaap.R
import com.kfouri.climaap.model.Daily
import kotlinx.android.synthetic.main.weather_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class WeatherDaysAdapter(val context: Context) : RecyclerView.Adapter<WeatherDaysAdapter.ViewHolder>() {

    private var list = ArrayList<Daily>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, context)
    }

    fun setData(newList: List<Daily>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(daily: Daily, context: Context) {

            val simpleDateFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
            itemView.textView_item_date.text = simpleDateFormat.format(daily.dt * 1000)
            itemView.textView_item_descripcion.text = daily.weather[0].main
            itemView.textView_item_max_min.text = context.getString(R.string.weather_max_min, String.format("%.1f", daily.temp.max), String.format("%.1f", daily.temp.min))

            GlideApp.with(context)
                    .load("http://openweathermap.org/img/wn/" + daily.weather[0].icon + "@4x.png")
                    .placeholder(R.drawable.loading_image)
                    .error(R.drawable.damaged_image)
                    .fitCenter()
                    .into(itemView.imageView_item_icon)
        }

    }
}