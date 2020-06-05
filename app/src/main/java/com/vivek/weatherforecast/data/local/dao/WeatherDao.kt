package com.vivek.weatherforecast.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity


@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherTable  ORDER BY id DESC LIMIT 1 ")
    fun getWeatherData(): LiveData<WeatherResponseEntity>


    @Query("SELECT * FROM WeatherTable  ORDER BY id DESC LIMIT 1 ")
    fun getWeatherDataSimple(): WeatherResponseEntity


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(WeatherResponse: WeatherResponseEntity)

}
