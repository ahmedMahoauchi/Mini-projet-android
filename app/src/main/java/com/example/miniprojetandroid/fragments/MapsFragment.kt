package com.example.miniprojetandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.miniprojetandroid.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->

        val henda = LatLng(37.316849, 9.869161)
        val tunisia = LatLng(36.806389, 10.181667)
        googleMap.addMarker(MarkerOptions()
            .position(tunisia).title("Marker in Sydney")
            .icon(BitmapDescriptorFactory.
            defaultMarker( BitmapDescriptorFactory.HUE_AZURE)))

        googleMap.addMarker(MarkerOptions()
            .position(henda).title("Marker in Sydney")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_station_marker)))

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(henda))
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