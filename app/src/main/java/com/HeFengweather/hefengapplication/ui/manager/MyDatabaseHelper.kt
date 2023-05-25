package com.HeFengweather.hefengapplication.ui.manager

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table Item (placeId text primary key," +
                "placeName text, tempe text, humidity text, weather text)")
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}
