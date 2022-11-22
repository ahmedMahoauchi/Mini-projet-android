package com.example.miniprojetandroid.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.*
import com.example.miniprojetandroid.adapters.UsersAdapter
import com.example.miniprojetandroid.entities.GetUserByEmailResponse
import com.example.miniprojetandroid.entities.LoginResponse
import com.example.miniprojetandroid.entities.User
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUsersFragment : Fragment() {
    var users:ArrayList<UserX> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_users, container, false)

        val rv=view.findViewById<RecyclerView>(R.id.users_recycler)

       getAllUser(rv)








        return view
    }

    private fun getAllUser(rv:RecyclerView) {
        rv.layoutManager= LinearLayoutManager(this.context)

        val apiInterface = ApiUser.create()

        apiInterface.getUsers().enqueue(object :
            Callback<User> {

            override fun onResponse(call: Call<User>, response:
            Response<User>
            ) {
                val user = response.body()?.users
                var adapter= user?.let { UsersAdapter(it,this@AdminUsersFragment.context) }
                rv.adapter=adapter


            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("server",t.toString())
            }


        })



    }


}