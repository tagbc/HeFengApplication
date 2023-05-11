package com.HeFengweather.hefengapplication.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.HeFengweather.hefengapplication.logic.Respository
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.geo.GeoBean.LocationBean

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()

    var placeList = ArrayList<LocationBean>()

    val placeLiveData = Transformations.switchMap(searchLiveData){
        query -> Respository.searchPlaces(query)
    }

    fun searchPlaces(query : String)
    {
        searchLiveData.value = query
    }
}