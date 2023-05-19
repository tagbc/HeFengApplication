package com.HeFengweather.hefengapplication.ui.weather

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.HeFengweather.hefengapplication.BootCompleteReceiver
import com.HeFengweather.hefengapplication.R
import com.HeFengweather.hefengapplication.WeatherInfoService
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather

class BootComListener: BDAbstractLocationListener() {
    lateinit var context:Context

    override fun onReceiveLocation(p0: BDLocation) {
        val city = p0.city
        val province = p0.province
        val district = p0.district
        val street = p0.street
        val msg = Message()
        val args = Bundle()
        msg.what=1
        val TAG = "GPS"
        val loacl  = city.toString() + district.toString()
        context = BootCompleteReceiver.context




    }
}