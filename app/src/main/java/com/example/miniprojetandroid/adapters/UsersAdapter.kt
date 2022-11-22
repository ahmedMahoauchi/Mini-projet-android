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
import com.example.miniprojetandroid.entities.UserX

class UsersAdapter(val users:ArrayList<UserX>, val context: Context? ) : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var username: TextView = itemView.findViewById(R.id.textView37)
        var email: TextView = itemView.findViewById(R.id.textView38)
        var kiosqueId: TextView = itemView.findViewById(R.id.textView41)
        var CIN: TextView = itemView.findViewById(R.id.textView39)
        var createdAt: TextView = itemView.findViewById(R.id.textView40)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.username.text=users[position].name
        holder.email.text=users[position].email
        if (!users[position].myKiosque.isEmpty()){
            holder.kiosqueId.text=users[position].myKiosque[0]
        }else{
            holder.kiosqueId.text="don't have a station"
        }
        holder.CIN.text=users[position].CIN
        holder.createdAt.text=users[position].createdAt
    }

    override fun getItemCount(): Int {
        return users.size
    }

}