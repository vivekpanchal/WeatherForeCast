/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vivek.weatherforecast.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.vivek.weatherforecast.data.local.converters.CurrentDataConverter
import com.vivek.weatherforecast.data.local.converters.DailyConverter
import com.vivek.weatherforecast.data.local.converters.WeatherConverter
import kotlinx.android.parcel.Parcelize


/**
 * Simple object to hold repo search responses. This is different from the Entity in the database
 * because we are keeping a search result in 1 row and denormalizing list of results into a single
 * column.
 */
@Parcelize
@Entity(tableName = "WeatherTable")
@JsonClass(generateAdapter = true)
data class WeatherResponseEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1,
    @TypeConverters(CurrentDataConverter::class)
    @Json(name = "current") val current: Current,
    @TypeConverters(DailyConverter::class)
    @Json(name = "daily") val daily: List<Daily>,
    @Json(name = "lat") val lat: Double,
    @Json(name = "lon") val lon: Double,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "timezone_offset") val timezoneOffset: Int
) : Parcelable {

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Current(
        @Json(name = "clouds") val clouds: Int,
        @Json(name = "dew_point") val dewPoint: Double,
        @Json(name = "dt") val dt: Int,
        @Json(name = "feels_like") val feelsLike: Double,
        @Json(name = "humidity") val humidity: Int,
        @Json(name = "pressure") val pressure: Int,
        @Json(name = "sunrise") val sunrise: Long,
        @Json(name = "sunset") val sunset: Long,
        @Json(name = "temp") val temp: Double,
        @Json(name = "uvi") val uvi: Double,
        @Json(name = "visibility") val visibility: Int,
        @TypeConverters(WeatherConverter::class)
        @Json(name = "weather") val weather: List<Weather>,
        @Json(name = "wind_deg") val windDeg: Int,
        @Json(name = "wind_speed") val windSpeed: Double
    ) : Parcelable {
        @Parcelize
        @JsonClass(generateAdapter = true)
        data class Weather(
            @Json(name = "description") val description: String,
            @Json(name = "icon") val icon: String,
            @Json(name = "id") val id: Int,
            @Json(name = "main") val main: String
        ) : Parcelable
    }

    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Daily(
        @Json(name = "clouds") val clouds: Int?,
        @Json(name = "dew_point") val dewPoint: Double?,
        @Json(name = "dt") val dt: Int?,
        @Json(name = "feels_like") val feelsLike: FeelsLike?,
        @Json(name = "humidity") val humidity: Int?,
        @Json(name = "pressure") val pressure: Int?,
        @Json(name = "rain") val rain: Double?,
        @Json(name = "sunrise") val sunrise: Int?,
        @Json(name = "sunset") val sunset: Int?,
        @Json(name = "temp") val temp: Temp?,
        @Json(name = "uvi") val uvi: Double?,
        @TypeConverters(WeatherConverter::class)
        @Json(name = "weather") val weather: List<Weather>?,
        @Json(name = "wind_deg") val windDeg: Int?,
        @Json(name = "wind_speed") val windSpeed: Double?
    ) : Parcelable {
        @Parcelize
        @JsonClass(generateAdapter = true)
        data class FeelsLike(
            @Json(name = "day") val day: Double,
            @Json(name = "eve") val eve: Double,
            @Json(name = "morn") val morn: Double,
            @Json(name = "night") val night: Double
        ) : Parcelable

        @JsonClass(generateAdapter = true)
        @Parcelize
        data class Temp(
            @Json(name = "day") val day: Double,
            @Json(name = "eve") val eve: Double,
            @Json(name = "max") val max: Double,
            @Json(name = "min") val min: Double,
            @Json(name = "morn") val morn: Double,
            @Json(name = "night") val night: Double
        ) : Parcelable

        @Parcelize
        @JsonClass(generateAdapter = true)
        data class Weather(
            @Json(name = "description") val description: String,
            @Json(name = "icon") val icon: String,
            @Json(name = "id") val id: Int,
            @Json(name = "main") val main: String
        ) : Parcelable
    }
}

