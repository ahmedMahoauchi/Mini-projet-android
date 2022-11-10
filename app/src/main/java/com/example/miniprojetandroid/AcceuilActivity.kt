package com.example.miniprojetandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.adapters.StationAdapter
import com.example.miniprojetandroid.entities.Station

class AcceuilActivity : AppCompatActivity() {
    var stations:ArrayList<Station> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)

        var rv: RecyclerView =findViewById(R.id.station_recycler)
        rv.layoutManager= LinearLayoutManager(this)


        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        var adapter=StationAdapter(stations,this)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,true)
        rv.layoutManager = layoutManager
        rv.adapter=adapter




    }
}