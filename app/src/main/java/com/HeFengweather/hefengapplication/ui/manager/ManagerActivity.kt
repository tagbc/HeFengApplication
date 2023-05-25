package com.HeFengweather.hefengapplication.ui.manager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.MainActivity
import com.HeFengweather.hefengapplication.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ManagerActivity : AppCompatActivity() {

    companion object{
        val messageList = ArrayList<Message>()
        var check = true
    }

    lateinit var adapter : MessageAdapter
    lateinit var manarecycleView : RecyclerView

    @SuppressLint("MissingInflatedId", "Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)

        val managerbar: Toolbar = findViewById(R.id.managerbar)
        setSupportActionBar(managerbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        manarecycleView = findViewById(R.id.manarecycleView)
        val layoutManager = GridLayoutManager(this, 1)


        adapter = MessageAdapter(this, messageList)
        manarecycleView.layoutManager = layoutManager
        manarecycleView.adapter = adapter

        if(check)
        {
            check = false
            var placeId : String? = intent.getStringExtra("placeId")
            var placeName : String? = intent.getStringExtra("placeName")
//            placeId = ""
//            placeName = ""
            val message = placeId?.let { placeName?.let { it1 -> Message(it, it1) } }
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
                message?.let { messageList.add(it) }
            }

        }

        val dpHelper = MyDatabaseHelper(this, "Weather.db", 1)
        val db = dpHelper.readableDatabase

        val messages = db.query("Item", null, null,
            null, null, null, null)
        if (messages.moveToFirst()) {
            do {
                val placeId = messages.getString(messages.getColumnIndex("placeId"))
                val placeName = messages.getString(messages.getColumnIndex("placeName"))
                val message = Message(placeId, placeName)
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
                    message?.let { messageList.add(it) }
                }
//                Log.d("ManagerActivity", placeId + placeName)
            } while (messages.moveToNext())
        }

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.toolbar, menu)
//        return true
//    }

    override fun onResume() {
        super.onResume()
        manarecycleView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.settings -> Toast.makeText(this, "You clicked Editor",
//                Toast.LENGTH_SHORT).show()
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}