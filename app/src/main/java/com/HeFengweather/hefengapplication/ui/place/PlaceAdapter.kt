package com.HeFengweather.hefengapplication.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.R
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.qweather.sdk.bean.geo.GeoBean

class PlaceAdapter(private val fragment: Fragment, private val placeList: List<GeoBean.LocationBean>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>()
{
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
//        val holder = ViewHolder(view)
//        holder.itemView.setOnClickListener {
//            val position = holder.adapterPosition
//            val place = placeList[position]
//            val activity = fragment.activity
//            if (activity is WeatherActivity) {
//                activity.findViewById<DrawerLayout>(R.id.drawerLayout).closeDrawers()
//                activity.viewModel.locationLng = place.location.lng
//                activity.viewModel.locationLat = place.location.lat
//                activity.viewModel.placeName = place.name
//                activity.refreshWeather()
//            } else {
//                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
//                    putExtra("location_lng", place.location.lng)
//                    putExtra("location_lat", place.location.lat)
//                    putExtra("place_name", place.name)
//                }
//                fragment.startActivity(intent)
//                activity?.finish()
//            }
//            fragment.viewModel.savePlace(place)
//        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.adm1
    }

    override fun getItemCount() = placeList.size
}