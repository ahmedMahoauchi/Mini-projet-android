package com.example.miniprojetandroid.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.example.miniprojetandroid.COOR
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.adapters.AdminKiosqueAdapter
import com.example.miniprojetandroid.entities.Kiosque
import com.example.miniprojetandroid.network.ApiUser

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InitialMapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val apiInterface = ApiUser.create()
        apiInterface.getKisoques().enqueue(object :
            Callback<Kiosque> {
            override fun onResponse(call: Call<Kiosque>, response:
            Response<Kiosque>
            ) {
                val user = response.body()
                for(i in user!!){
                    val coordonne = i.coordenation
                    val latLng = coordonne.split(",").toTypedArray()

                    val latitude = latLng[0].toDouble()
                    val longitude = latLng[1].toDouble()

                    googleMap.addMarker(
                        MarkerOptions()

                            .position(LatLng(latitude, longitude))
                            .title("lat")
                            .icon(
                                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                            )
                    )
                }

            }
            override fun onFailure(call: Call<Kiosque>, t: Throwable) {
                Log.e("server",t.toString())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_initial_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }



}

