package com.HeFengweather.hefengapplication.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.HeFengweather.hefengapplication.logic.Respository
import com.qweather.sdk.bean.geo.GeoBean

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<GeoBean.LocationBean>()

    val placeLiveData = Transformations.switchMap(searchLiveData){
        query -> Respository.searchPlaces(query)
    }

    fun searchPlaces(query : String)
    {
        searchLiveData.value = query
    }
}