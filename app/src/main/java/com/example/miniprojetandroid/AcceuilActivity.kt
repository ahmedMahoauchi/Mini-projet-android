package com.example.miniprojetandroid

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.adapters.NearestAdapter
import com.example.miniprojetandroid.adapters.StationAdapter
import com.example.miniprojetandroid.entities.Station
import com.google.android.material.bottomsheet.BottomSheetBehavior

class AcceuilActivity : AppCompatActivity() {
    var stations:ArrayList<Station> = ArrayList()
    var stations1:ArrayList<Station> = ArrayList()

    private lateinit var mSharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acceuil)
        mSharedPref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        
        Toast.makeText(this,mSharedPref.getString("USER_KIOSQUE","hello"),Toast.LENGTH_SHORT).show()

        val bottom = findViewById<FrameLayout>(R.id.sheet_bottom)
        BottomSheetBehavior.from(bottom).apply {
            peekHeight = 500
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        var rv: RecyclerView =findViewById(R.id.station_recycler)
        var rv1: RecyclerView =findViewById(R.id.nearestRecycler)
        rv.layoutManager= LinearLayoutManager(this)
        rv1.layoutManager= LinearLayoutManager(this)


        stations.add(Station(R.drawable.kiosque,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))
        stations.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque2,"ahri",1.2,1.2))
        stations1.add(Station(R.drawable.kiosque1,"ahri",1.2,1.2))

        var adapter=StationAdapter(stations,this)
        var adapter1=NearestAdapter(stations1,this)
        val layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val layoutManager1 = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rv.layoutManager = layoutManager
        rv1.layoutManager = layoutManager1

        rv.adapter=adapter
        rv1.adapter=adapter1

    }
}