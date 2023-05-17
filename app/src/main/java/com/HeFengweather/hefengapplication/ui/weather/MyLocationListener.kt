package com.HeFengweather.hefengapplication.ui.weather

import android.os.Bundle
import android.os.Message
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation



class MyLocationListener: BDAbstractLocationListener() {
    override fun onReceiveLocation(p0: BDLocation) {
        val city = p0.city
        val province = p0.province
        val district = p0.district
        val street = p0.street
        val msg = Message()
        val args = Bundle()
        args.putString("district",district)
        msg.data = args
        msg.what=1
        WeatherActivity.handler.sendMessage(msg)

    }
}