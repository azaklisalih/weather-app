package com.salih.weatherapp.service

import com.salih.weatherapp.model.WeatherModel
import com.salih.weatherapp.model.result

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {
    private val BASE_URL ="https://api.collectapi.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getData(lang : String,cityName : String) : Single<WeatherModel> {
        return api.getWeather(lang,cityName)
    }
}