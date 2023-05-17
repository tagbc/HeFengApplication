package com.HeFengweather.hefengapplication.ui.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.HeFengweather.hefengapplication.R
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.IndicesType
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.indices.IndicesBean
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.HeConfig
import com.qweather.sdk.view.QWeather
import java.util.*
import kotlin.concurrent.thread


class WeatherActivity : AppCompatActivity() {
    val SkyList = ArrayList<Sky>()
    @SuppressLint("MissingInflatedId", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
//      获取id
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsingToolbar)

        val forecastlayout: LinearLayout = findViewById(R.id.forecastLayout)

        // TODO: 下拉刷新
        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipeRefresh)
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            initdata()
            Thread.sleep(500)
            swipeRefresh.isRefreshing = false
        }
//      赋值
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbar.title = intent.getStringExtra("query")

        initdata()
    }

    private fun touch(text: TextView) {
        text.movementMethod = ScrollingMovementMethod.getInstance()
        text.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                //通知父控件不要干扰,即屏蔽父控件的该事件以及该事件之后的一切action
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            if (event.action == MotionEvent.ACTION_MOVE) {
                //通知父控件不要干扰,即屏蔽父控件的该事件以及该事件之后的一切action
                v.parent.requestDisallowInterceptTouchEvent(true)
            }
            if (event.action == MotionEvent.ACTION_UP) {
                v.parent.requestDisallowInterceptTouchEvent(false)
            }
            false
        })
    }
    private fun initdata()
    {
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        val collapsingToolbar: CollapsingToolbarLayout = findViewById(R.id.collapsingToolbar)
        val skyBackground: ImageView = findViewById(R.id.skyBackground)
        val currentSky: TextView = findViewById(R.id.currentSky)
        val maxTemp: TextView = findViewById(R.id.maxTemp)
        val minTemp: TextView = findViewById(R.id.minTemp)
        val aqiData: TextView = findViewById(R.id.aqiData)
        val aqiLevel: TextView = findViewById(R.id.aqiLevel)
        val mainPollute: TextView = findViewById(R.id.mainPollute)
        val pm2_5Data: TextView = findViewById(R.id.pm2_5Data)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val forecastlayout: LinearLayout = findViewById(R.id.forecastLayout)
        val urayData: TextView = findViewById(R.id.uryData)
        val urayLevel: TextView = findViewById(R.id.uryLevel)
        val urayInfo: TextView = findViewById(R.id.uryInfo)
        val sunsetTime: TextView = findViewById(R.id.sunsetTime)
        val sunriseTime: TextView = findViewById(R.id.sunriseTime)
        val rainfallData: TextView = findViewById(R.id.rainfallData)
        val rainfallForecast: TextView = findViewById(R.id.rainfallForecast)
        val windRate: TextView = findViewById(R.id.windRate)
        val windWay: TextView = findViewById(R.id.windWay)
        val bodyTemp: TextView = findViewById(R.id.bodyTemp)
        val bodyTempInfo: TextView = findViewById(R.id.bodyTempInfo)
        val humidityPer: TextView = findViewById(R.id.humidityPer)
        val humidityInfo: TextView = findViewById(R.id.humidityInfo)
        val visibility: TextView = findViewById(R.id.visibility)
        val visibilityInfo: TextView = findViewById(R.id.visibilityInfo)
        val airPresure: TextView = findViewById(R.id.airPresure)

        touch(urayInfo)
        touch(bodyTempInfo)
        touch(humidityInfo)
        touch(visibilityInfo)


        HeConfig.init("HE2305112021361191", "f922eb1c50c04dd093e6b9a50e240578")
        HeConfig.switchToDevService()

        val id = intent.getStringExtra("placeid")
        val TAG = "WeatherActivity"
        QWeather.getWeatherNow(this,id,object : QWeather.OnResultWeatherNowListener{
            override fun onError(weatherBean: Throwable?) {
                Log.i(TAG,"getWeatherNow error")
            }

            override fun onSuccess(weatherBean: WeatherNowBean?) {
                if(Code.OK==weatherBean?.code)
                {
                    val now = weatherBean.now
                    runOnUiThread {
                        bodyTemp.text = now.feelsLike + "℃"
                        humidityPer.text = now.humidity
                        humidityInfo.text = "目前露点温度为"+ now.dew + "℃"
                        visibility.text = now.vis + "公里"
                        airPresure.text = now.pressure + "百帕"
                        windWay.text = now.windDir + " 风向角度:" + now.wind360 + "°"
                        windRate.text = now.windSpeed + "公里/小时"
                        rainfallData.text = "过去二十四小时" + now.precip + "毫米"
                        currentSky.text = now.text

                        var background = R.drawable.bg_partly_cloudy_night
                        if(now.text.contains("晴"))
                        {
                            background = R.drawable.bg_clear_day
                        }
                        else if(now.text.contains("云") || now.text.contains("阴"))
                        {
                            background = R.drawable.bg_cloudy
                        }
                        else if(now.text.contains("雾"))
                        {
                            background = R.drawable.bg_fog
                        }
                        else if(now.text.contains("雨"))
                        {
                            background = R.drawable.bg_rain
                        }
                        else if(now.text.contains("雪"))
                        {
                            background = R.drawable.bg_snow
                        }
                        else if(now.text.contains("风"))
                        {
                            background = R.drawable.bg_wind
                        }

                        skyBackground.setBackgroundResource(background)

                    }
                }
                else
                {
                    val code = weatherBean?.code
                    Log.i(TAG,"failed code $code")
                }
            }

        })

        QWeather.getWeather24Hourly(this,id,object :QWeather.OnResultWeatherHourlyListener{
            override fun onError(p0: Throwable?) {
                Log.i(TAG,"getWeather24Hourly error")
            }

            override fun onSuccess(p0: WeatherHourlyBean?) {
                if(p0?.code==Code.OK)
                {
                    runOnUiThread{
                        rainfallForecast.text = "预计下一个小时降水概率:" + p0.hourly[0].pop + "%"
                    }
                }
                else
                {
                    val code = p0?.code
                    Log.i(TAG,"failed code $code")
                }
            }

        })
        var indiceslist = listOf<IndicesType>(IndicesType.UV,IndicesType.COMF,IndicesType.PTFC)

        QWeather.getIndices1D(this,id, Lang.ZH_HANS,indiceslist,object :QWeather.OnResultIndicesListener{
            override fun onError(p0: Throwable?) {
                Log.i(TAG,"getIndices1D error")
            }

            override fun onSuccess(p0: IndicesBean?) {
                if(p0?.code==Code.OK)
                {
                    runOnUiThread{
                        val dailyBean = p0.dailyList[0]
                        val dailyBean2 = p0.dailyList[1]
                        val dailyBean3 = p0.dailyList[2]

                        urayData.text = dailyBean.level
                        urayLevel.text = dailyBean.category
                        urayInfo.text = dailyBean.text

                        bodyTempInfo.text = dailyBean2.text

                        visibilityInfo.text = dailyBean3.text
                    }
                }
                else
                {
                    val code = p0?.code
                    Log.i(TAG,"failed code $code")
                }
            }

        })

        QWeather.getWeather7D(this,id,object : QWeather.OnResultWeatherDailyListener{
            override fun onError(p0: Throwable?) {
                Log.i(TAG,"getWeather10D error")
            }

            override fun onSuccess(p0: WeatherDailyBean?) {
                if(p0?.code==Code.OK)
                {
                    runOnUiThread{
                        val dailylist = p0.daily
                        sunriseTime.text = dailylist[0].sunrise
                        sunsetTime.text = dailylist[0].sunset

                        maxTemp.text = "最高气温" + dailylist[0].tempMax +"°"
                        minTemp.text = "最低气温" + dailylist[0].tempMin +"°"

                        for(i in 0 until 7) {
                            val view =  LayoutInflater.from(this@WeatherActivity).inflate(R.layout.forecastsky_item,
                                forecastlayout, false)
                            val time = view.findViewById(R.id.time) as TextView
                            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
                            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
                            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
                            time.text = dailylist[i].fxDate
                            var ic = R.drawable.ic_clear_night
                            skyInfo.text = dailylist[i].textDay
                            var t = dailylist[i].textDay
                            if(t.contains("云"))
                            {
                                ic = R.drawable.ic_cloudy
                            }
                            else if(t.contains("晴"))
                            {
                                ic = R.drawable.ic_clear_day
                            }
                            else if(t.contains("雨"))
                            {
                                ic = R.drawable.ic_heavy_rain
                            }
                            else if(t.contains("雪"))
                            {
                                ic = R.drawable.ic_heavy_snow
                            }
                            else if(t.contains("雷"))
                            {
                                ic = R.drawable.ic_thunder_shower
                            }
                            else if(t.contains("霾")){
                                ic = R.drawable.ic_heavy_haze
                            }
                            else if(t.contains("雾"))
                            {
                                ic = R.drawable.ic_fog
                            }
                            else if(t.contains("阴"))
                            {
                                ic = R.drawable.ic_partly_cloud_night
                            }
                            skyIcon.setImageResource(ic)
                            var maxt = dailylist[i].tempMax
                            var mint = dailylist[i].tempMin
                            temperatureInfo.text = mint + "°" + "~" + maxt + "°"
                            forecastlayout.addView(view)
                        }

                    }
                }
                else
                {
                    val code = p0?.code
                    Log.i(TAG,"failed code $code")
                }
            }

        })

        QWeather.getAirNow(this,id,Lang.ZH_HANS,object : QWeather.OnResultAirNowListener{
            override fun onError(p0: Throwable?) {
                Log.i(TAG,"getAirNow error")
            }

            override fun onSuccess(p0: AirNowBean?) {
                if(p0?.code==Code.OK)
                {
                    runOnUiThread{

                        val now = p0.now

                        aqiData.text = now.level
                        aqiLevel.text = now.category
                        mainPollute.text = now.primary
                        pm2_5Data.text = now.pm2p5

                    }
                }
                else
                {
                    val code = p0?.code
                    Log.i(TAG,"failed code $code")
                }
            }


        })

        SkyList.clear()
        QWeather.getWeather24Hourly(this,id,object : QWeather.OnResultWeatherHourlyListener{
            override fun onError(p0: Throwable?) {
                Log.i(TAG,"getWeather24Hourly")
            }

            override fun onSuccess(p0: WeatherHourlyBean?) {
                if(p0?.code==Code.OK)
                {
                    runOnUiThread{
                        val hourly = p0.hourly
                        val calendar: Calendar = Calendar.getInstance()
                        var h = calendar[Calendar.HOUR_OF_DAY]
                        for(i in 0 until 12)
                        {
                            val hour = hourly[i]
                            h += 1
                            h %= 24
                            val str = h.toString() + ":00"
                            val t = hour.text
                            var ic = R.drawable.ic_clear_night
                            if(t.contains("云"))
                            {
                                ic = R.drawable.ic_cloudy
                            }
                            else if(t.contains("晴"))
                            {
                                ic = R.drawable.ic_clear_day
                                if(h >= 18 || h <= 4) {
                                    ic = R.drawable.ic_clear_night
                                }
                            }
                            else if(t.contains("雨"))
                            {
                                ic = R.drawable.ic_heavy_rain
                            }
                            else if(t.contains("雪"))
                            {
                                ic = R.drawable.ic_heavy_snow
                            }
                            else if(t.contains("雷"))
                            {
                                ic = R.drawable.ic_thunder_shower
                            }
                            else if(t.contains("霾")){
                                ic = R.drawable.ic_heavy_haze
                            }
                            else if(t.contains("雾"))
                            {
                                ic = R.drawable.ic_fog
                            }
                            else if(t.contains("阴"))
                            {
                                ic = R.drawable.ic_partly_cloud_night
                            }
                            val s = Sky(str, ic, t)
                            SkyList.add(s)
                        }
                        val layoutManager = LinearLayoutManager(this@WeatherActivity)
                        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                        recyclerView.layoutManager = layoutManager
                        val adapter = SkyAdapter(this@WeatherActivity, SkyList)
                        recyclerView.adapter = adapter
                    }
                }
                else
                {
                    val code = p0?.code
                    Log.i(TAG,"failed code $code")
                }
            }

        })


    }


    private fun refresh() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }



}