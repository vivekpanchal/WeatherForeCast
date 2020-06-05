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

package com.vivek.weatherforecast.data.remote.api

import androidx.lifecycle.LiveData
import com.vivek.weatherforecast.data.remote.model.ApiResponse
import com.vivek.weatherforecast.data.local.entity.WeatherResponseEntity
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points
 */
interface WeatherService {


    @GET("onecall")
     fun getWeatherResponse(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String,
        @Query("units")units:String
    ): LiveData<ApiResponse<WeatherResponseEntity>>


}
