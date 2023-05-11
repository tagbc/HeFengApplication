package com.HeFengweather.hefengapplication.logic.model

data class PlaceResponse(val code : Int,val location : List<Place>) // code==200时请求成功

data class Place(val name : String,val id : String,val lat : String,val lon : String,val adm2 : String,val adm1 : String,val country : String)