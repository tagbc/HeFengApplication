package com.HeFengweather.hefengapplication

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class HeFengweatherApplication : Application() {
    companion object{
        const val TOKEN = "a6d4442b07a44469b6fc350297dbe87b"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}