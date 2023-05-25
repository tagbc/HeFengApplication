package com.HeFengweather.hefengapplication.ui.place

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.HeFengweatherApplication
import com.HeFengweather.hefengapplication.R
import com.HeFengweather.hefengapplication.ui.manager.ManagerActivity.Companion.messageList
import com.HeFengweather.hefengapplication.ui.manager.MyDatabaseHelper
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.qweather.sdk.bean.air.AirNowBean
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<GeoBean.LocationBean>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>()
{
    lateinit var placeId : String;
    lateinit var placeName : String
    lateinit var weather : String
    lateinit var temp : String
    lateinit var humidity : String




    val activity = fragment.activity

//    val handler = object : Handler(){
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            when(msg.what){
//                1 -> activity?.onBackPressed()
//            }
//        }
//    }

    val TAG = "searchCity"
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]


//            val intent = Intent(parent.context, WeatherActivity::class.java)
//            intent.putExtra("query",place.name)
//            intent.putExtra("placeid",place.id)
//            intent.putExtra("source","PlaceFragment")
//            fragment.viewModel.saveplace = place
//            fragment.startActivity(intent)

            placeName = place.name
            placeId = place.id

            val message: com.HeFengweather.hefengapplication.ui.manager.Message =
                com.HeFengweather.hefengapplication.ui.manager.Message(placeId, placeName)



            var noRepeat = true
            for (mes in messageList)
            {
                if (mes.placeId==placeId)
                {
                    noRepeat = false
                }
            }
            if(noRepeat)
            {
                messageList.add(message)
            }

            activity?.onBackPressed()

//            QWeather.getWeatherNow(parent.context, placeId, object : QWeather.OnResultWeatherNowListener {
//                override fun onError(weatherBean: Throwable?) {
//                    Log.i(TAG, "getWeatherNow error")
//                }
//
//                override fun onSuccess(weatherBean: WeatherNowBean?) {
//                    if (Code.OK == weatherBean?.code) {
//                        val now = weatherBean.now
//                        temp = now.temp + "°"
//                        weather = now.text
//                        humidity = "相对湿度" + now.humidity
//
//                        db.execSQL("insert into Item (placeId, placeName, tempe, humidity, weather)" +
//                                "values(?, ?, ?, ?, ?)", arrayOf(placeId, placeName, temp, humidity,
//                            weather)
//                        )
//
//
//                        val msg = Message()
//                        msg.what = 1
//                        handler.sendMessage(msg)
//
//                    } else {
//                        val code = weatherBean?.code
//                        Log.i(TAG, "failed code $code")
//                    }
//                }
//
//            })

            

        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.adm1
    }

    override fun getItemCount() = placeList.size
}