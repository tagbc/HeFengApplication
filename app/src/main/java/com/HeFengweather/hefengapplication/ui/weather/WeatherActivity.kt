package com.HeFengweather.hefengapplication.ui.weather

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.HeFengweather.hefengapplication.R
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.bean.geo.*
import com.qweather.sdk.view.QWeather

class WeatherActivity : AppCompatActivity() {
    val TAG = "Test"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
//        var weather : TextView = findViewById(R.id.weather)
//        QWeather.getWeatherNow(this@WeatherActivity, "101010300", Lang.ZH_HANS, Unit.METRIC,
//            object : QWeather.OnResultWeatherNowListener {
//                override fun onError(e: Throwable) {
//                    Log.i(TAG, "getWeather onError: $e")
//                }
//
//                override fun onSuccess(weatherBean: WeatherNowBean) {
//                    Log.i(TAG, "getWeather onSuccess: " + Gson().toJson(weatherBean))
//
////                    runOnUiThread({weather.text = weatherBean.now.cloud})
//
//
////                    weather.text = "getWeather onSuccess: " + Gson().toJson(weatherBean)
//                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
//                    if (Code.OK == weatherBean.code) {
//                        val now = weatherBean.now
//                    } else {
//                        //在此查看返回数据失败的原因
//                        val code = weatherBean.code
//                        Log.i(TAG, "failed code: $code")
//                    }
//                }
//            }
//            )
        QWeather.getGeoCityLookup(this,"南海区",object : QWeather.OnResultGeoListener{
            override fun onError(e: Throwable) {
                Log.i(TAG, "getCity onError: $e")
            }

            override fun onSuccess(geoBean : GeoBean) {
                Log.i(TAG, "getWeather onSuccess: " + Gson().toJson(geoBean))

//                    runOnUiThread({weather.text = geoBean.locationBean.get(0).id})


//                    weather.text = "getWeather onSuccess: " + Gson().toJson(weatherBean)
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK == geoBean.code) {
                    val place = geoBean.locationBean
                } else {
                    //在此查看返回数据失败的原因
                    val code = geoBean.code
                    Log.i(TAG, "failed code: $code")
                }
            }
        })
    }
    companion object{
        fun actionStart(context: Context)
        {
            val intent = Intent(context,WeatherActivity::class.java)

            context.startActivity(intent)
        }

    }
}