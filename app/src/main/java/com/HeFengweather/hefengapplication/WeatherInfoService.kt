package com.HeFengweather.hefengapplication

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.HeFengweather.hefengapplication.ui.weather.MyLocationListener
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption

class WeatherInfoService : Service() {

    private val mBinder = WeatherInfoBinder()

    class WeatherInfoBinder:Binder(){

    }

    val notificationId:Int = 1

    lateinit var mLocationClient: LocationClient
    val myListener: MyLocationListener = MyLocationListener()
    val option = LocationClientOption()
    lateinit var placeId:String
    lateinit var placeName:String
    lateinit var notification:NotificationCompat.Builder
    companion object{
        lateinit var context:Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
        val manager =
            getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "Weather_service", "天气通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, WeatherActivity::class.java)
        val pi = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        notification = NotificationCompat.Builder(this, "Weather_service")
            .setSmallIcon(R.drawable.ic_hefeng)
            .setContentIntent(pi)



    }

    override fun onBind(intent: Intent?): IBinder {
        return mBinder
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        notification.setContentTitle(intent.getStringExtra("placeName").toString()+"市")
        notification.setContentText(intent.getStringExtra("feelsLike")+"°"+"  " + intent.getStringExtra("currentSky"))
        notification.setGroup("group1").setGroupSummary(false)
        startForeground(notificationId, notification.build())



        return super.onStartCommand(intent, flags, startId)
    }



}