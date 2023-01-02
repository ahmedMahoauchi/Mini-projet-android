package com.example.miniprojetandroid.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.entities.Kiosque
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors
import kotlin.collections.ArrayList

class UsersAdapter(var users:ArrayList<UserX>, val context: Context? ) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var username: TextView = itemView.findViewById(R.id.textView37)
        var email: TextView = itemView.findViewById(R.id.textView38)
        var img: ImageView = itemView.findViewById(R.id.roundedImageView)
        var delete: ImageView = itemView.findViewById(R.id.roundedImageView)
        //var kiosqueId: TextView = itemView.findViewById(R.id.textView41)
        //var CIN: TextView = itemView.findViewById(R.id.textView39)
        //var createdAt: TextView = itemView.findViewById(R.id.textView40)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return MyViewHolder(view)
    }

    fun filterList(filterlist: ArrayList<UserX>) {
        // below line is to add our filtered
        // list in our course array list.
        users = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text=users[position].name
        holder.email.text=users[position].email

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null
        executor.execute {
            val imageURL = users[position]?.image?.url
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)
                handler.post {
                    holder.img.setImageBitmap(image)
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    override fun getItemCount(): Int {
        return users.size
    }


   

}