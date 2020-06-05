package com.vivek.weatherforecast.data.Repository

import androidx.lifecycle.LiveData
import com.vivek.weatherforecast.data.local.dao.WeatherDao
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity
import com.vivek.weatherforecast.data.remote.api.WeatherService
import com.vivek.weatherforecast.utils.AppExecutors
import com.vivek.weatherforecast.utils.Constants
import com.vivek.weatherforecast.utils.Resource
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val api: WeatherService,
    private val appExecutors: AppExecutors,
    private val weatherDao: WeatherDao
) {

    fun getWeatherResponse(lat: Double, lon: Double): LiveData<Resource<WeatherResponseEntity>> {
        return object :
            NetworkBoundResource<WeatherResponseEntity, WeatherResponseEntity>(appExecutors) {

            override fun saveCallResult(item: WeatherResponseEntity) {
                Timber.d("data inserting.....")
                weatherDao.insertData(item)
            }

            override fun shouldFetch(data: WeatherResponseEntity?): Boolean {
                return true;
            }

            override fun loadFromDb() = weatherDao.getWeatherData()
            override fun createCall() =
                api.getWeatherResponse(
                    lat,
                    lon,
                    "hourly",
                    Constants.NetworkService.API_KEY_VALUE,
                    "metric"
                )

        }.asLiveData()
    }


}