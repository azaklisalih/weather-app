package com.salih.weatherapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salih.weatherapp.model.result
import com.salih.weatherapp.model.WeatherModel

import com.salih.weatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SelectedLocationViewModel : ViewModel(){
    val weatherModel = MutableLiveData<WeatherModel>()
    val weatherErrorMessage = MutableLiveData<Boolean>()
    val weatherLoading = MutableLiveData<Boolean>()

    private val weatherAPIService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    fun refleshData(lang:String,cityName: String){
        UploadDataViaInternet(lang,cityName)

    }
    private fun UploadDataViaInternet(lang :String,cityName : String){
        weatherLoading.value = true
       disposable.add(
           weatherAPIService.getData(lang,cityName)
               .subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeWith(object : DisposableSingleObserver<WeatherModel>(){
                   override fun onSuccess(t: WeatherModel) {
                        weatherModel.value = t
                       weatherErrorMessage.value = false
                       weatherLoading.value = false

                   }

                   override fun onError(e: Throwable) {
                       weatherErrorMessage.value = true
                       weatherLoading.value = false
                       e.printStackTrace()
                       println("ERROR!")
                   }

               })
       )

    }

}