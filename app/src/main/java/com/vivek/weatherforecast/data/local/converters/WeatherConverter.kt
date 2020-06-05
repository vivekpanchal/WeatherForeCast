package com.vivek.weatherforecast.data.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity

object WeatherConverter {

    @TypeConverter
    @JvmStatic
    fun stringToList(data: String?): List<WeatherResponseEntity.Current.Weather>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(
            List::class.java,
            WeatherResponseEntity.Current.Weather::class.java
        )
        val adapter = moshi.adapter<List<WeatherResponseEntity.Current.Weather>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun listToString(objects: List<WeatherResponseEntity.Current.Weather>): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type = Types.newParameterizedType(
            List::class.java,
            WeatherResponseEntity.Current.Weather::class.java
        )
        val adapter = moshi.adapter<List<WeatherResponseEntity.Current.Weather>>(type)
        return adapter.toJson(objects)
    }
}