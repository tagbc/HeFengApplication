package com.HeFengweather.hefengapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity

class RainWarningReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val manager =
            context.getSystemService(Context.NOTIFICATION_SERVICE)
                    as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "RainWarning", "下雨通知",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(context, WeatherActivity::class.java)
        val pi = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "RainWarning")
            .setSmallIcon(R.drawable.bg_rain)
            .setContentIntent(pi)
            .setContentTitle("过会预计有雨，请记得带伞")
            .build()
        manager.notify(213,notification)
    }


}