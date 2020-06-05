package com.vivek.weatherforecast.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vivek.weatherforecast.data.Repository.WeatherRepository
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity
import com.vivek.weatherforecast.utils.Resource
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repo: WeatherRepository) : ViewModel() {


    fun getWeatherResponse(lat: Double, long: Double): LiveData<Resource<WeatherResponseEntity>> {
        return repo.getWeatherResponse(lat, long)
    }
}