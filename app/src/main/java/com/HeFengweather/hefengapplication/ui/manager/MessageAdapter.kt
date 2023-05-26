package com.HeFengweather.hefengapplication.ui.manager

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.R
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>) :
    RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    val dpHelper = MyDatabaseHelper(context, "Weather.db", 1)
    val db = dpHelper.writableDatabase


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messagename: TextView = view.findViewById(R.id.messagename)
        val delete:Button = view.findViewById(R.id.delete)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.manager_item, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = messageList[position]
            val intent = Intent(parent.context, WeatherActivity::class.java)
            intent.putExtra("query",place.placeName)
            intent.putExtra("placeid",place.placeId)
            intent.putExtra("source","ManagerActivity")
            context.startActivity(intent)

        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messageList[position]
        holder.messagename.text = message.placeName
//        holder.messagetemp.text = message.tempe
//        holder.messagehum.text = message.humidity
//        holder.messagewea.text = message.weather


        db.execSQL("replace into Item (placeId, placeName)" +
                "values(?, ?)", arrayOf(messageList[position].placeId, messageList[position].placeName)
        )

//        Log.d("check", messageList[position].placeId + messageList[position].placeName)

        holder.delete.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("注意")
                setMessage("您确定要删除吗？")
                setCancelable(false)
                setPositiveButton("是") { dialog, which ->
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show()
                    messageList.removeAt(position)
                    db.execSQL("delete from Item where placeId = ?", arrayOf(message.placeId))
                    notifyItemRemoved(position)
                    notifyDataSetChanged()
                }
                setNegativeButton("否") { dialog, which ->

                }
                show()
            }

        }


    }

    override fun getItemCount() = messageList.size


}