package com.example.miniprojetandroid.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.adapters.AdminKiosqueAdapter
import com.example.miniprojetandroid.adapters.PendingAdapter
import com.example.miniprojetandroid.entities.Kisoque
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AdminPendingKiosqueFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin_pending_kiosque, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.pending_recycler)

        getAllUser(rv)





        return view
    }

    private fun getAllUser(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(this.context)

        val apiInterface = ApiUser.create()

        apiInterface.getKisoques().enqueue(object :
            Callback<Kisoque> {

            override fun onResponse(
                call: Call<Kisoque>, response:
                Response<Kisoque>
            ) {
                val user = response.body()
                var adapter =
                    user?.let { PendingAdapter(it, this@AdminPendingKiosqueFragment.context) }
                rv.adapter = adapter


            }

            override fun onFailure(call: Call<Kisoque>, t: Throwable) {
                Log.e("server", t.toString())
            }


        })


    }
}

