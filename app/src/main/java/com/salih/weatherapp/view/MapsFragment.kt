package com.salih.weatherapp.view

import android.Manifest
import android.app.Activity
import android.app.Application
import android.app.MediaRouteActionProvider
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.salih.weatherapp.R
import kotlinx.android.synthetic.main.fragment_maps.*
import java.lang.Exception
import java.util.*
import java.util.zip.Inflater
import kotlin.collections.ArrayList

class MapsFragment : Fragment(), OnMapReadyCallback {
    lateinit private var locationManager: LocationManager
    lateinit private var locationListener: LocationListener
    lateinit private var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        var currentCity: String?
        var currentLng: String?

        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {

            override fun onLocationChanged(p0: Location) {
                mMap.clear()
                SearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                            val geocoder = Geocoder(activity?.applicationContext, Locale.getDefault())
                            val adressList1 = geocoder.getFromLocationName(query,1)

                            val adres = adressList1.get(0)
                            var searchLatlng = LatLng(adres.latitude,adres.longitude)
                            mMap.addMarker(MarkerOptions().position(searchLatlng).title("secili konum"))
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchLatlng, 15f))

                                var currentcity = adres.adminArea
                                if (currentcity != null){
                                    val action = MapsFragmentDirections.actionMapsFragmentToNavSelectedLocation(currentcity)
                                    findNavController().navigate(action)
                                }

                        println(adres.adminArea)
                        return true
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                       println("girdi")
                        return false
                    }

                })

               /* try {
                    val adressList = geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
                    if (adressList.size > 0) {
                        currentLng = adressList.get(0).locale.toString().substring(0, 2)


                        currentCity = adressList.get(0).adminArea.toString()
                        println(currentCity)

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                */
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
                100000f,
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
