package com.salih.weatherapp.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salih.weatherapp.R
import com.salih.weatherapp.adapter.SelectedLocationRecyclerAdapter
import com.salih.weatherapp.model.WeatherModel
import com.salih.weatherapp.model.result
import com.salih.weatherapp.viewModel.SelectedLocationViewModel
import kotlinx.android.synthetic.main.fragment_selected_location.*
import kotlinx.android.synthetic.main.l_recycler_row.*
import kotlinx.android.synthetic.main.sl_recycler_row.*
import java.util.*
import kotlin.collections.ArrayList


class SelectedLocation : Fragment() {
    private lateinit var prefence: SharedPreferences
    private lateinit var viewModel: SelectedLocationViewModel
    private val recyclerSelectedLocationAdapter =
        SelectedLocationRecyclerAdapter(WeatherModel(ArrayList<result>()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selected_location, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SelectedLocationViewModel::class.java)

        activity?.let {
            prefence = it.getSharedPreferences(it.packageName, Context.MODE_PRIVATE)
        }
        var cityname = "trabzon"
        val lang = "tr"
        /* arguments?.let {
             val currentcity = SelectedLocationArgs.fromBundle(it).cityname
             prefence.edit().putString("cityname2",currentcity).apply()
         }

         cityname = prefence.getString("cityname2","trabzon").toString()*/

        viewModel.refleshData(lang, cityname)

        selectedLocationRecycler.layoutManager = LinearLayoutManager(context)
        selectedLocationRecycler.adapter = recyclerSelectedLocationAdapter

        swipeRefleshLayout2.setOnRefreshListener {
            slErrorMessage.visibility = View.GONE
            slProgressBar.visibility = View.VISIBLE
            selectedLocationRecycler.visibility = View.GONE
            swipeRefleshLayout2.isRefreshing = false
            viewModel.refleshData(lang, cityname)
            sladress.text = cityname
        }
        observeLiveData()

        fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_nav_selectedLocation_to_mapsFragment)
        }
    }

    fun observeLiveData() {
        viewModel.weatherModel.observe(viewLifecycleOwner, Observer {
            it?.let {
                selectedLocationRecycler.visibility = View.VISIBLE
                slErrorMessage.visibility = View.GONE
                slProgressBar.visibility = View.GONE

                recyclerSelectedLocationAdapter.weatherListUpdate(it.result)
            }
        })
        viewModel.weatherLoading.observe(viewLifecycleOwner, Observer {
            if (it) {
                slErrorMessage.visibility = View.GONE
                selectedLocationRecycler.visibility = View.GONE
                slProgressBar.visibility = View.VISIBLE
            } else {
                slProgressBar.visibility = View.GONE
            }
        })
        viewModel.weatherErrorMessage.observe(viewLifecycleOwner, Observer {
            if (it) {
                slErrorMessage.visibility = View.VISIBLE
                selectedLocationRecycler.visibility = View.GONE
            } else {
                slErrorMessage.visibility = View.GONE
            }
        })

    }


}