package com.example.miniprojetandroid.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miniprojetandroid.*
import com.example.miniprojetandroid.adapters.UsersAdapter
import com.example.miniprojetandroid.entities.DeleteUserResponse
import com.example.miniprojetandroid.entities.Kiosque
import com.example.miniprojetandroid.entities.User
import com.example.miniprojetandroid.entities.UserX
import com.example.miniprojetandroid.network.ApiUser
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminUsersFragment : Fragment() {
    var users:ArrayList<UserX> = ArrayList()
    lateinit var adapter: UsersAdapter
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
        val searchBar=view.findViewById<EditText>(R.id.editText)

       getAllUser(rv)

        searchBar.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                filter(searchBar.text.toString())
            }
        })








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
                adapter= user?.let { UsersAdapter(it,this@AdminUsersFragment.context) }!!
                users = user!!

                adapter?.notifyDataSetChanged()

                // on below line we are creating a method to create item touch helper
                // method for adding swipe to delete functionality.
                // in this we are specifying drag direction and position to right
                // on below line we are creating a method to create item touch helper
                // method for adding swipe to delete functionality.
                // in this we are specifying drag direction and position to right
                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        // this method is called
                        // when the item is moved.
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        // this method is called when we swipe our item to right direction.
                        // on below line we are getting the item at a particular position.

                        // below line is to get the position
                        // of the item at that position.
                        val position = viewHolder.adapterPosition

                        // this method is called when item is swiped.
                        // below line is to remove item from our array list.
                        user?.removeAt(viewHolder.adapterPosition)

                        // below line is to notify our item is removed from adapter.
                        adapter?.notifyItemRemoved(viewHolder.adapterPosition)

                        // below line is to display our snackbar with action.
                        // below line is to display our snackbar with action.
                        // below line is to display our snackbar with action.
                        user?.get(position)?.let { deleteUser(it._id) }
                    }
                    // at last we are adding this
                    // to our recycler view.
                }).attachToRecyclerView(rv)

                rv.adapter=adapter


            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("server",t.toString())
            }


        })



    }

    fun deleteUser(id :String){
        val apiInterface = ApiUser.create()
        apiInterface.deleteUser(id).enqueue(object :
            Callback<DeleteUserResponse> {
            override fun onResponse(call: Call<DeleteUserResponse>, response:
            Response<DeleteUserResponse>
            ) {
                val user = response.body()
                Toast.makeText(context, user.toString(), Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
                Log.e("server",t.toString())
            }
        })
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<UserX> = ArrayList()

        // running a for loop to compare elements.
        for (item in users) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(context, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }

}