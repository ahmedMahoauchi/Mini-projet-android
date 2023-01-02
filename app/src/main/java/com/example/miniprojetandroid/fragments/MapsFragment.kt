package com.example.miniprojetandroid.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.miniprojetandroid.COOR
import com.example.miniprojetandroid.PREF_NAME
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.USER_COORD
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    lateinit var mSharedPref: SharedPreferences
    private val callback = OnMapReadyCallback { googleMap ->

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        val coordonne = mSharedPref.getString(COOR, "10,10").toString()


        val latLng = coordonne.split(",").toTypedArray()

        val latitude = latLng[0].toDouble()
        val longitude = latLng[1].toDouble()

        val tunisia = LatLng(latitude, longitude)

        googleMap.addMarker(
            MarkerOptions()

                .position(tunisia)
                .title("lat")
                .icon(
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                )
        )

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(tunisia))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

}