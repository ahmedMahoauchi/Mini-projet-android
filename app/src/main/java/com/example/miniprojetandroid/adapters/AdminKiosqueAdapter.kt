package com.example.miniprojetandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.Kiosque

class AdminKiosqueAdapter(val stations: Kiosque, val context: Context? ) : BaseAdapter() {



    private var layoutInflater: LayoutInflater? = null
    private lateinit var name: TextView
    private lateinit var img: ImageView






    override fun getCount(): Int {
        return stations.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }



    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var convertView = p1
        // is null, if it is null we are initializing it.
        if (layoutInflater == null) {
            layoutInflater =
                context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        // on the below line we are checking if convert view is null.
        // If it is null we are initializing it.
        if (convertView == null) {
            // on below line we are passing the layout file
            // which we have to inflate for each item of grid view.
            convertView = layoutInflater!!.inflate(R.layout.station_item, null)
        }
        // on below line we are initializing our course image view
        // and course text view with their ids.
        img = convertView?.findViewById(R.id.station_img)!!
        name = convertView.findViewById(R.id.station_name)
        // on below line we are setting image for our course image view.
        img.setImageResource(R.drawable.kiosque)
        // on below line we are setting text in our course text view.
        name.setText(stations.get(p0).name)

        // at last we are returning our convert view.
        return convertView
    }


}