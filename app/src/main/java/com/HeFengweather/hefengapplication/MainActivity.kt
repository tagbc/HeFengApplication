package com.HeFengweather.hefengapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.HeFengweather.hefengapplication.logic.Respository
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather


class MainActivity : AppCompatActivity() {
    val TAG = "Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HeConfig.init("HE2305112021361191", "f922eb1c50c04dd093e6b9a50e240578")
        HeConfig.switchToDevService();
        setContentView(R.layout.activity_main)

    }

}



