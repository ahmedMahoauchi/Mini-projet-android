package com.example.miniprojetandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.Kisoque

class PendingAdapter(val stations: Kisoque, val context: Context? ) : RecyclerView.Adapter<PendingAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.station_img)
        var name: TextView = itemView.findViewById(R.id.station_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.station_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (stations[position].Status == 0){
            holder.name.text=stations[position].name
            holder.img.setImageResource(R.drawable.kiosque)
        }
        holder.name.text=stations[position].name
        holder.img.setImageResource(R.drawable.kiosque)
    }

    override fun getItemCount(): Int {
        return stations.size
    }



}