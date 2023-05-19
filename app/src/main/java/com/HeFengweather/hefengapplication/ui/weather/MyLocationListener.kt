package com.HeFengweather.hefengapplication.ui.weather

import android.os.Bundle
import android.os.Message
import android.util.Log
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather


class MyLocationListener: BDAbstractLocationListener() {
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
        QWeather.getGeoCityLookup(
            WeatherActivity.weatherActivity,city.toString(),
            object : QWeather.OnResultGeoListener {
                override fun onError(e: Throwable) {
                    Log.i(TAG, "getCity onError: $e")
                }

                override fun onSuccess(geoBean : GeoBean?) {
                    Log.i(TAG, "getCity onSuccess: " + Gson().toJson(geoBean))
                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK == geoBean?.code) {
                        val places = geoBean.locationBean
                        if(places != null)
                        {
                            val place = places[0]
                            for ( temp in places)
                            {
                                Log.d("place",temp.name)
                            }
//                                            weatherActivity.initdata(place.id)
                            args.putString("placeId",place.id)
                            args.putString("placeName",place.name)
                            msg.data = args
                            WeatherActivity.handler.sendMessage(msg)
                        }
                    } else {
                        //在此查看返回数据失败的原因
                        val code = geoBean?.code
                        Log.i(TAG, "failed code: $code")
                    }
                }
            })


    }
}