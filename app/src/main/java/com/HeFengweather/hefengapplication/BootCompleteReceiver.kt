package com.HeFengweather.hefengapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.HeFengweather.hefengapplication.ui.weather.BootComListener
import com.HeFengweather.hefengapplication.ui.weather.MyLocationListener
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

class BootCompleteReceiver : BroadcastReceiver() {

    lateinit var mLocationClient: LocationClient
    val myListener: BootComListener = BootComListener()
    val option = LocationClientOption()
    lateinit var placeId:String
    lateinit var placeName:String
    companion object{
        lateinit var context: Context
    }

    override fun onReceive(context1: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        context=context1
        option.setIsNeedAddress(true)
        option.setNeedNewVersionRgc(true)
        mLocationClient = LocationClient(context)
        mLocationClient.registerLocationListener(myListener)
        mLocationClient.setLocOption(option)
        mLocationClient.start()




    }


}