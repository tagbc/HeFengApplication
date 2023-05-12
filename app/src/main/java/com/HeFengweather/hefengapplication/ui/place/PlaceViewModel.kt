package com.HeFengweather.hefengapplication.ui.place

import androidx.lifecycle.ViewModel
import com.qweather.sdk.bean.geo.GeoBean.LocationBean

class PlaceViewModel : ViewModel() {

    var placeList = ArrayList<LocationBean>()
    var saveplace = LocationBean()
}