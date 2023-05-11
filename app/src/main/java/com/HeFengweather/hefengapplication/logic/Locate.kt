package com.HeFengweather.hefengapplication.logic

object Locate {
    fun getLocal() : String
    {
        // 返回本地城市ID
        return searchLocal()
    }

    fun searchLocal(): String
    {
        return "101280803"
    }

}