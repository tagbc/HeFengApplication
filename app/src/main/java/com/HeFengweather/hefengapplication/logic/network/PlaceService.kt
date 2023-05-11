package com.HeFengweather.hefengapplication.logic.network

import com.HeFengweather.hefengapplication.HeFengweatherApplication
import com.HeFengweather.hefengapplication.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/city/lookup?lang=zh&key=${HeFengweatherApplication.TOKEN}")
    fun searchPlaces(@Query("query")query: String) : Call<PlaceResponse>
}