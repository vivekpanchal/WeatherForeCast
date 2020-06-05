package com.vivek.weatherforecast.data.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity


object DailyConverter {

    @TypeConverter
    @JvmStatic
    fun DailyStringToList(data: String?): List<WeatherResponseEntity.Daily>? {
        if (data == null) {
            return emptyList()
        }

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type =
            Types.newParameterizedType(List::class.java, WeatherResponseEntity.Daily::class.java)
        val adapter = moshi.adapter<List<WeatherResponseEntity.Daily>>(type)
        return adapter.fromJson(data)
    }

    @TypeConverter
    @JvmStatic
    fun DailyListToString(objects: List<WeatherResponseEntity.Daily>): String {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val type =
            Types.newParameterizedType(List::class.java, WeatherResponseEntity.Daily::class.java)
        val adapter = moshi.adapter<List<WeatherResponseEntity.Daily>>(type)
        return adapter.toJson(objects)
    }
}
