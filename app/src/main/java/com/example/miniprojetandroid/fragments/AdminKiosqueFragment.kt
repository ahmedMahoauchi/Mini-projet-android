package com.example.miniprojetandroid.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.AllKiosqueDetailActivity
import com.example.miniprojetandroid.DetatilsKiosqueActivity
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.adapters.AdminKiosqueAdapter
import com.example.miniprojetandroid.entities.Kiosque
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminKiosqueFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_kiosque, container, false)
        val rv=view.findViewById<GridView>(R.id.kiosque_recycler)

        getAllUser(rv)










        return view
    }

    private fun getAllUser(rv:GridView) {
        val apiInterface = ApiUser.create()
        apiInterface.getKisoques().enqueue(object :
            Callback<Kiosque> {
            override fun onResponse(call: Call<Kiosque>, response:
            Response<Kiosque>
            ) {
                val user = response.body()
                var adapter= user?.let { AdminKiosqueAdapter(it,this@AdminKiosqueFragment.context) }
                rv.adapter=adapter
                rv.setOnItemClickListener { adapterView, view, position, id ->

                    val  intent = Intent(context,AllKiosqueDetailActivity::class.java)
                    intent.putExtra("id", user!![position]._id)
                    startActivity(intent)

                }
            }
            override fun onFailure(call: Call<Kiosque>, t: Throwable) {
                Log.e("server",t.toString())
            }
        })



    }

}