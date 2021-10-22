package com.salih.weatherapp.adapter

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

class LocationRecyclerAdapter(val weatherlist : WeatherModel) : RecyclerView.Adapter<LocationRecyclerAdapter.LViewHolder>() {
    var location ="Location"
    class LViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
    }

     fun changeLocation(location:String){
        this.location = location
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.l_recycler_row,parent,false)
        return LViewHolder(view)
    }

    override fun onBindViewHolder(holder: LViewHolder, position: Int) {

        holder.itemView.lHumidity.text = weatherlist.result.get(position).humidity
        holder.itemView.lMax.text = weatherlist.result.get(position).max
        holder.itemView.lMin.text = weatherlist.result.get(position).min
        holder.itemView.lStatus.text = weatherlist.result.get(position).status
        holder.itemView.ldegree.text = weatherlist.result.get(position).degree.substring(0,2) + "°C"
        holder.itemView.lday.text = weatherlist.result.get(position).day
        //holder.itemView.ldate.text = weatherlist.result.get(position).date
        holder.itemView.lNight.text= weatherlist.result.get(position).night
        holder.itemView.ladress.text= location
        if (position != 0){
            holder.itemView.ladress.visibility = View.GONE

        }else{
            holder.itemView.ladress.visibility = View.VISIBLE
        }

        if(weatherlist.result.get(position).status == "Clear"){

             //holder.itemView.slIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/890/890347.png",
            //makeplaceholder(holder.itemView.context))
            holder.itemView.lIconView.setImageResource(R.drawable.clear)

        }else if(weatherlist.result.get(position).status == "Rain"){
            //holder.itemView.lIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/3217/3217172.png",
            // makeplaceholder(holder.itemView.context))
            holder.itemView.lIconView.setImageResource(R.drawable.rain)
        }else if(weatherlist.result.get(position).status == "Clouds"){
            //holder.itemView.lIconView.downloadIcon("https://cdn-icons-png.flaticon.com/512/1163/1163675.png",
            //makeplaceholder(holder.itemView.context))
            holder.itemView.lIconView.setImageResource(R.drawable.clouds)
        } else{
             holder.itemView.lIconView.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    override fun getItemCount(): Int {
        return  weatherlist.result.size
    }

    fun weatherListUpdate(newWeatherList : List<result>){
        weatherlist.result.clear()
        weatherlist.result.addAll(newWeatherList)
        notifyDataSetChanged()
    }

}