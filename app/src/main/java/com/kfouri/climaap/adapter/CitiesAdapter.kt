package com.kfouri.climaap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kfouri.climaap.R
import com.kfouri.climaap.model.CityModel
import kotlinx.android.synthetic.main.city_item.view.*

class CitiesAdapter(private val clickListener: (CityModel) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = ArrayList<CityModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = list[position]
        (holder as CityViewHolder).bind(item, clickListener)
    }

    fun setData(newList: ArrayList<CityModel>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    class CityViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        fun bind(city: CityModel, clickListener: (CityModel) -> Unit){
            itemView.textView_item_name.text = city.name
            itemView.setOnClickListener { clickListener(city) }
        }
    }

}