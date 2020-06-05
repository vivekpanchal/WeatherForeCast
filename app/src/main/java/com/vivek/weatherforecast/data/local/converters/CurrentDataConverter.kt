package com.vivek.weatherforecast.data.local.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity
import timber.log.Timber


object CurrentDataConverter {

    @TypeConverter
    @JvmStatic
    fun StringToObject(data: String?): WeatherResponseEntity.Current? {
        if (data == null) {
            return null
        } else {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
//        val type = Types.newParameterizedType(WeatherResponseEntity.Current::class.java, WeatherResponseEntity.Current::class.java)
//        val adapter = moshi.adapter<WeatherResponseEntity.Current>(type)
//        return adapter.fromJson(data)

            val adapter: JsonAdapter<WeatherResponseEntity.Current> = moshi.adapter(
                WeatherResponseEntity.Current::class.java
            )

            val obj: WeatherResponseEntity.Current? = adapter.fromJson(data);
            Timber.d("obj ${obj.toString()}")
            return obj
        }

    }

    @TypeConverter
    @JvmStatic
    fun ObjectToString(objects: WeatherResponseEntity.Current): String {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val adapter: JsonAdapter<WeatherResponseEntity.Current> =
            moshi.adapter(WeatherResponseEntity.Current::class.java)

        val str: String = adapter.toJson(objects)
        Timber.d("string :$str")
        return str
    }
}