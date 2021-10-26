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


import kotlinx.android.synthetic.main.sl_recycler_row.view.*
import java.util.*

class SelectedLocationRecyclerAdapter(val weatherlist: WeatherModel) :
    RecyclerView.Adapter<SelectedLocationRecyclerAdapter.SlViewHolder>() {
    var location = "trabzon"
    class SlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
    fun changeLocation(location:String){
        this.location = location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.sl_recycler_row, parent, false)
        return SlViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlViewHolder, position: Int) {

        /* holder.itemView.slHumidity.text = weatherlist.get(position).result.get(position).humidity
         holder.itemView.slMax.text = weatherlist.get(position).result.get(position).icon
         holder.itemView.slMin.text = weatherlist.get(position).result.get(position).status
         holder.itemView.slStatus.text = weatherlist.get(position).result.get(position).degree
         holder.itemView.sldegree.text = weatherlist.get(position).result.get(position).night
         holder.itemView.slday.text = weatherlist.get(position).result.get(position).day
         holder.itemView.sldate.text = weatherlist.get(position).result.get(position).date*/

        holder.itemView.slHumidity.text = weatherlist.result.get(position).humidity
        holder.itemView.slMax.text = weatherlist.result.get(position).max
        holder.itemView.slMin.text = weatherlist.result.get(position).min
        holder.itemView.slStatus.text = weatherlist.result.get(position).status
        holder.itemView.sldegree.text =
            weatherlist.result.get(position).degree.substring(0, 2) + "°C"
        holder.itemView.slday.text = weatherlist.result.get(position).day
        //holder.itemView.sldate.text = weatherlist.result.get(position).date
        holder.itemView.slNight.text = weatherlist.result.get(position).night
        holder.itemView.sladress.text = location
        //holder.itemView.slIconView.downloadIcon(weatherlist.result.get(position).icon,
        //makeplaceholder(holder.itemView.context))


        if (weatherlist.result.get(position).status == "Clear") {

            // holder.itemView.slIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/890/890347.png",
            //makeplaceholder(holder.itemView.context))
            holder.itemView.slIconView.setImageResource(R.drawable.clear)


        } else if (weatherlist.result.get(position).status == "Rain") {
            //holder.itemView.slIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/3217/3217172.png",
            // makeplaceholder(holder.itemView.context))
            holder.itemView.slIconView.setImageResource(R.drawable.rain)
        } else if (weatherlist.result.get(position).status == "Clouds") {
            //holder.itemView.slIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/1163/1163675.png",
            //makeplaceholder(holder.itemView.context))
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