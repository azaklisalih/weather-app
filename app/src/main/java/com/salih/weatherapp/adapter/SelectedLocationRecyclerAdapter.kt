package com.salih.weatherapp.adapter

import android.content.res.Configuration
import android.view.LayoutInflater


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.salih.weatherapp.R
import com.salih.weatherapp.model.WeatherModel
import com.salih.weatherapp.model.result
import com.salih.weatherapp.util.downloadIcon
import com.salih.weatherapp.util.makeplaceholder
import kotlinx.android.synthetic.main.l_recycler_row.view.*


import kotlinx.android.synthetic.main.sl_recycler_row.view.*
import java.util.*

class SelectedLocationRecyclerAdapter(val weatherlist: WeatherModel) :
    RecyclerView.Adapter<SelectedLocationRecyclerAdapter.SlViewHolder>() {
    var location = "trabzon"

    class SlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun changeLocation(location: String) {
        this.location = location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sl_recycler_row, parent, false)
        return SlViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlViewHolder, position: Int) {

        holder.itemView.slHumidity.text = weatherlist.result.get(position).humidity
        holder.itemView.slMax.text = weatherlist.result.get(position).max
        holder.itemView.slMin.text = weatherlist.result.get(position).min
        holder.itemView.slStatus.text = weatherlist.result.get(position).status
        holder.itemView.sldegree.text =
            weatherlist.result.get(position).degree.substring(0, 2) + "°C"
        holder.itemView.slday.text = weatherlist.result.get(position).day
        holder.itemView.slNight.text = weatherlist.result.get(position).night
        holder.itemView.sladress.text = location

        if (position != 0){
            holder.itemView.sladress.visibility = View.GONE

        }else{
            holder.itemView.sladress.visibility = View.VISIBLE
        }

        if (weatherlist.result.get(position).status == "Clear") {

            holder.itemView.slIconView.setImageResource(R.drawable.clear)

        } else if (weatherlist.result.get(position).status == "Rain") {

            holder.itemView.slIconView.setImageResource(R.drawable.rain)

        } else if (weatherlist.result.get(position).status == "Clouds") {

            holder.itemView.slIconView.setImageResource(R.drawable.clouds)
        }
    }

    override fun getItemCount(): Int {
        return weatherlist.result.size
    }

    fun weatherListUpdate(newWeatherList: List<result>) {
        weatherlist.result.clear()
        weatherlist.result.addAll(newWeatherList)
        notifyDataSetChanged()
    }
}