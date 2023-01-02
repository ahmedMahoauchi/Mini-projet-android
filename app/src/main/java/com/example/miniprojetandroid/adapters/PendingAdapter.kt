package com.example.miniprojetandroid.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.AcceuilActivity
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.Kiosque
import com.example.miniprojetandroid.entities.Station

class PendingAdapter(val stations: Kiosque, val context: Context? ) : RecyclerView.Adapter<PendingAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun  setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }


    class MyViewHolder(itemView: View,listener : onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.station_img)
        var name: TextView = itemView.findViewById(R.id.station_name)
        var address: TextView = itemView.findViewById(R.id.textView38)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.admin_kiosque_item,parent,false)
        return MyViewHolder(view,mListener)
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            holder.name.text=stations[position].name
            holder.address.text=stations[position].adresse
            holder.img.setImageResource(R.drawable.kiosque)


    }

    override fun getItemCount(): Int {
        return stations.size
    }



}