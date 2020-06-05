package com.vivek.weatherforecast.data.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity

object DailyWeatherConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<WeatherResponseEntity.Daily.Weather>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(
            List::class.java,
            WeatherResponseEntity.Daily.Weather::class.java
        )
        val adapter = moshi.adapter<List<WeatherResponseEntity.Daily.Weather>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<WeatherResponseEntity.Daily.Weather>): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(
            List::class.java,
            WeatherResponseEntity.Daily.Weather::class.java
        )
        val adapter = moshi.adapter<List<WeatherResponseEntity.Daily.Weather>>(type)
        return adapter.toJson(objects)
    }
}