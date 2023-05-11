package com.HeFengweather.hefengapplication.ui.place

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.HeFengweather.hefengapplication.HeFengweatherApplication
import com.HeFengweather.hefengapplication.MainActivity
import com.HeFengweather.hefengapplication.R
import com.HeFengweather.hefengapplication.ui.weather.WeatherActivity
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Code
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Unit
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather

class PlaceFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }
    private lateinit var adapter: PlaceAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        if (activity is MainActivity && viewModel.isPlaceSaved()) {
//            val place = viewModel.getSavedPlace()
//            val intent = Intent(context, WeatherActivity::class.java).apply {
//                putExtra("location_lng", place.location.lng)
//                putExtra("location_lat", place.location.lat)
//                putExtra("place_name", place.name)
//            }
//            startActivity(intent)
//            activity?.finish()
//            return
//        }

        val layoutManager = LinearLayoutManager(activity)
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter = adapter
        val searchPlaceEdit : EditText = requireView().findViewById(R.id.searchPlaceEdit)
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if(content.isNotEmpty())
            {
                searchPlace(content)
                Log.d("Respository","123123")
            }
            else
            {
                recyclerView.visibility = View.GONE
                val bgImageView : ImageView = requireView().findViewById(R.id.bgImageView)
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }



//        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result->
//            val places = result.getOrNull()
//            if(places != null)
//            {
//                recyclerView.visibility = View.VISIBLE
//                val bgImageView : ImageView = requireView().findViewById(R.id.bgImageView)
//                bgImageView.visibility = View.GONE
//                viewModel.placeList.clear()
//                viewModel.placeList.addAll(places)
//                adapter.notifyDataSetChanged()
//            }else
//            {
//                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//            Log.d("Respository",result.toString())
//        })
    }
    fun searchPlace(content : String)
    {
        val TAG = "Request of city"
        QWeather.getGeoCityLookup(context,content,
            object : QWeather.OnResultGeoListener {
                override fun onError(e: Throwable) {
                    Log.i(TAG, "getWeather onError: $e")
                }

                override fun onSuccess(geoBean : GeoBean?) {
                    Log.i(TAG, "getWeather onSuccess: " + Gson().toJson(geoBean))
                    //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                    if (Code.OK == geoBean?.code) {
                        val places = geoBean.locationBean
                        if(places != null)
                        {
                            activity?.runOnUiThread{
                                val recyclerView: RecyclerView = requireView().findViewById(R.id.recyclerView)
                                recyclerView.visibility = View.VISIBLE
                                val bgImageView : ImageView = requireView().findViewById(R.id.bgImageView)
                                bgImageView.visibility = View.GONE
                                viewModel.placeList.clear()
                                viewModel.placeList.addAll(places)
                                adapter.notifyDataSetChanged()
                            }
                        }else
                        {
                            Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        //在此查看返回数据失败的原因
                        val code = geoBean?.code
                        Log.i(TAG, "failed code: $code")
                    }
                }
            })
    }
}