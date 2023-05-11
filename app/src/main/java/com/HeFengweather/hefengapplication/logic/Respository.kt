package com.HeFengweather.hefengapplication.logic

import android.util.Log
import androidx.lifecycle.liveData
import com.HeFengweather.hefengapplication.HeFengweatherApplication
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.Dispatchers

object Respository {
    val TAG = "Respository"
    fun searchPlaces(query:String) = liveData(Dispatchers.IO)
    {
        HeConfig.init("HE2305071045211116", "303e06671a2348088bc08be7f4ad2ea2")
        HeConfig.switchToDevService()
        val result = try {

            lateinit var placeResponse : GeoBean

            QWeather.getGeoCityLookup(HeFengweatherApplication.context,query,object : QWeather.OnResultGeoListener{
                override fun onError(e: Throwable) {
                    Log.i(TAG, "getCity onError: $e")
//                    placeResponse = null
                }

                override fun onSuccess(geoBean : GeoBean) {
                    Log.i(TAG, "getCity onSuccess: " + Gson().toJson(geoBean))
                    placeResponse = geoBean

                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK == geoBean.code) {
//                        val places = geoBean.locationBean
//                        Result.success(places)

                    } else {
                        //在此查看返回数据失败的原因

                        val code = geoBean.code
                        Log.i(TAG, "failed code: $code")
//                        Result.failure(RuntimeException("response code os ${geoBean.code}"))
                    }
                }
            })
            if(placeResponse.code==Code.OK)
            {
                Result.success(placeResponse.locationBean)
            } else {
                Result.failure(RuntimeException("response code os ${placeResponse.code}"))
            }
        }
        catch (e : Exception)
        {
            Result.failure<List<GeoBean.LocationBean>>(e)
        }
        Log.i(TAG, result.toString())
        emit(result)
    }
}