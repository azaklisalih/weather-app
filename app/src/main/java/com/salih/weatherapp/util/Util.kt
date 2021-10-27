package com.salih.weatherapp.util

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.model.LatLng
import com.salih.weatherapp.R
import com.salih.weatherapp.view.LocationFragment
import com.salih.weatherapp.view.SelectedLocation
import java.lang.Exception
import java.util.*
lateinit private var locationManager: LocationManager
lateinit private var locationListener: LocationListener
private lateinit var prefence: SharedPreferences

fun ImageView.downloadIcon(url : String?,placeholder: CircularProgressDrawable){                          // icon indir
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)            //progressbar
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun makeplaceholder(context : Context) : CircularProgressDrawable{             //Progressbar

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

fun LocationFragment.addLocation(): String? {

    var currentLng = ""
    var currentCity=""
    activity?.let {
        prefence = it.getSharedPreferences(it.packageName, Context.MODE_PRIVATE)
    }

    locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    locationListener = object : LocationListener {
    override fun onLocationChanged(p0: Location) {

        //val currentLocation = LatLng(p0.latitude, p0.longitude)
        val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())

        try {
            val adressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
            if (adressList.size > 0) {
                currentLng = adressList.get(0).locale.toString().substring(0, 1)
                prefence.edit().putString("lang2", currentLng).apply()

                currentCity = adressList.get(0).adminArea.toString()
                prefence.edit().putString("cityname2", currentCity).apply()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
if (activity?.let {
    ContextCompat.checkSelfPermission(
        it.applicationContext,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
} != PackageManager.PERMISSION_GRANTED) {
    requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
} else {
    locationManager.requestLocationUpdates(
        LocationManager.GPS_PROVIDER,
        1,
        1000f,
        locationListener
    )
    val lastKnownLocation =
        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    if (lastKnownLocation != null) {
        val lastKnownLocationLatLng =
            LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }
}
return prefence.getString("cityname2","ankara")
}


fun SelectedLocation.addLocationSelected(): String? {
    var currentLng = ""
    var currentCity=""
    activity?.let {
        prefence = it.getSharedPreferences(it.packageName, Context.MODE_PRIVATE)
    }

    locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    locationListener = object : LocationListener {
        override fun onLocationChanged(p0: Location) {

            //val currentLocation = LatLng(p0.latitude, p0.longitude)
            val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())

            try {
                val adressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
                if (adressList.size > 0) {
                    currentLng = adressList.get(0).locale.toString().substring(0, 1)
                    prefence.edit().putString("lang3", currentLng).apply()

                    currentCity = adressList.get(0).adminArea.toString()
                    prefence.edit().putString("cityname3", currentCity).apply()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    if (activity?.let {
            ContextCompat.checkSelfPermission(
                it.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
    } else {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1,
            1000f,
            locationListener
        )
        val lastKnownLocation =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            val lastKnownLocationLatLng =
                LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
        }
    }
    return prefence.getString("cityname3","ankara")
}
