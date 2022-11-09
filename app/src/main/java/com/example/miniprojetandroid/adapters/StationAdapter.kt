package com.example.miniprojetandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.Station

class StationAdapter(val stations:ArrayList<Station>, val context: Context? ) : RecyclerView.Adapter<StationAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.station_img)
        var name: TextView = itemView.findViewById(R.id.station_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.station_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=stations[position].name
        holder.img.setImageResource(stations[position].img)
    }

    override fun getItemCount(): Int {
        return stations.size
    }

}