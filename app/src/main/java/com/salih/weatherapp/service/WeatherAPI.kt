package com.salih.weatherapp.service

import com.salih.weatherapp.model.WeatherModel
import com.salih.weatherapp.model.result

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface WeatherAPI {
    //"https://api.collectapi.com/weather/getWeather?data.lang=tr&data.city=ankara"
    @Headers("content-type:application/json","authorization:apikey 33eJlLL7BNr5gYKutN6aJl:7bjs0rRn0JMk736oxyj7be")
    @GET("weather/getWeather")


    fun getWeather(@Query("data.lang") lang : String ,@Query("data.city") cityName : String) : Single<WeatherModel>

}