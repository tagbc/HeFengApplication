package com.HeFengweather.hefengapplication.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.R
import com.bumptech.glide.Glide

class SkyAdapter(val context: Context, val SkyList: List<Sky>) :
        RecyclerView.Adapter<SkyAdapter.ViewHolder>() {

            inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val time: TextView = view.findViewById(R.id.time)
                val skyicon: ImageView = view.findViewById(R.id.skyIcon)
                val nowtemp: TextView = view.findViewById(R.id.nowtemp)
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(context).inflate(R.layout.forecasttemp_item,
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sky = SkyList[position]
        holder.time.text = sky.time
        holder.nowtemp.text = sky.nowtemp
        Glide.with(context).load(sky.imageID).into(holder.skyicon)
    }

    override fun getItemCount() = SkyList.size
}