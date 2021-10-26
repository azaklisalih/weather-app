package com.salih.weatherapp.view

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.salih.weatherapp.R
import com.salih.weatherapp.adapter.LocationRecyclerAdapter
import com.salih.weatherapp.model.WeatherModel
import com.salih.weatherapp.model.result
import com.salih.weatherapp.util.addLocation
import com.salih.weatherapp.viewModel.LocationViewModel
import kotlinx.android.synthetic.main.fragment_location.*
import kotlinx.android.synthetic.main.l_recycler_row.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class LocationFragment : Fragment() {
    lateinit private var locationManager: LocationManager
    lateinit private var locationListener: LocationListener
    private lateinit var viewModel: LocationViewModel
    private val recyclerLocationAdapter = LocationRecyclerAdapter(WeatherModel(ArrayList<result>()))
    private lateinit var prefence: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            prefence = it.getSharedPreferences(it.packageName, Context.MODE_PRIVATE)
        }

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        var currentCity = ""
        var currentLng = ""


        arguments?.let {
            currentCity = LocationFragmentArgs.fromBundle(it).cityname
            prefence.edit().putString("cityname", currentCity).apply()
            recyclerLocationAdapter.changeLocation(currentCity)
        }

        var cityname = prefence.getString("cityname", "ankara")
        var lang = prefence.getString("lang", "tr")

        if (lang != null && cityname != null) {
            viewModel.refleshData(lang, cityname)
            recyclerLocationAdapter.changeLocation(cityname)
        }
        locationRecycler.layoutManager = LinearLayoutManager(context)
        locationRecycler.adapter = recyclerLocationAdapter

        swipeRefleshLayout1.setOnRefreshListener {
            lErrorMessage.visibility = View.GONE
            lProgressBar.visibility = View.VISIBLE
            locationRecycler.visibility = View.GONE
            swipeRefleshLayout1.isRefreshing = false

            prefence.edit().putString("cityname2",addLocation())

            var g_currentLng = prefence.getString("lang2", lang)
            var g_currentCity = prefence.getString("cityname2", cityname)
            if (g_currentCity != null && g_currentLng != null) {
                viewModel.refleshData(g_currentLng, g_currentCity)
                recyclerLocationAdapter.changeLocation(g_currentCity)
            }
        }

        observeLiveData()
    }

    fun observeLiveData() {
        viewModel.weatherModel.observe(viewLifecycleOwner, Observer {
            it?.let {
                locationRecycler.visibility = View.VISIBLE
                lErrorMessage.visibility = View.GONE
                lProgressBar.visibility = View.GONE

                recyclerLocationAdapter.weatherListUpdate(it.result)
            }
        })
        viewModel.weatherLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                lErrorMessage.visibility = View.GONE
                locationRecycler.visibility = View.GONE
                lProgressBar.visibility = View.VISIBLE
            } else {
                lProgressBar.visibility = View.GONE
            }

        })
        viewModel.weatherErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it) {
                lErrorMessage.visibility = View.VISIBLE
                locationRecycler.visibility = View.GONE

            } else {
                lErrorMessage.visibility = View.GONE
            }
        })
    }

}