package com.salih.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.salih.weatherapp.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.l_recycler_row.*
import java.util.*


class SplashScreen : Fragment() {

    lateinit private var locationManager: LocationManager
    lateinit private var locationListener: LocationListener
    private lateinit var prefence: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let {
            prefence = it.getSharedPreferences(it.packageName, Context.MODE_PRIVATE)
        }

        var currentCity = ""
        var currentLng = ""
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(p0: Location) {

                //val currentLocation = LatLng(p0.latitude, p0.longitude)
                val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())

                try {
                    val adressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
                    if (adressList.size > 0) {
                        currentLng = adressList.get(0).locale.toString().substring(0, 1)
                        prefence.edit().putString("lang", currentLng).apply()

                        currentCity = adressList.get(0).adminArea.toString()
                        prefence.edit().putString("cityname1", currentCity).apply()
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

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        Handler(Looper.myLooper()!!).postDelayed({
            var cityname =  prefence.getString("cityname1","ankara")
            var c_cityname = prefence.getString("cityname1",cityname)

            if (c_cityname != null){
                val action = SplashScreenDirections.actionSplashScreen2ToNavLoc(c_cityname)
                findNavController().navigate(action)
            }

        },1000)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0) {
                if (activity?.let {
                        ContextCompat.checkSelfPermission(
                            it.applicationContext,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    } == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        100000,
                        100f,
                        locationListener
                    )
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


}