package com.HeFengweather.hefengapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qweather.sdk.view.HeConfig


class MainActivity : AppCompatActivity() {
    val TAG = "Main"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HeConfig.init("HE2305112021361191", "f922eb1c50c04dd093e6b9a50e240578")
        HeConfig.switchToDevService();
        setContentView(R.layout.activity_main)

    }

}



