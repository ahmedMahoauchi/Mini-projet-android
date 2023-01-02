package com.example.miniprojetandroid.fragments.userFragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.miniprojetandroid.PREF_NAME
import com.example.miniprojetandroid.R
import com.example.miniprojetandroid.USER_KIOSQUE
import com.example.miniprojetandroid.entities.AddProductResponse
import com.example.miniprojetandroid.entities.AddProductToKiosqueResponse
import com.example.miniprojetandroid.entities.AddServiceResponse
import com.example.miniprojetandroid.entities.AddServiceToKiosque
import com.example.miniprojetandroid.network.ApiUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddServiceFragment : Fragment() {

    lateinit var name : EditText
    lateinit var price : EditText
    lateinit var mSharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_service, container, false)
        val back = view.findViewById<ImageView>(R.id.imageView21)


        name = view.findViewById(R.id.name)
        price = view.findViewById(R.id.price)

        mSharedPref = requireContext().getSharedPreferences(PREF_NAME, AppCompatActivity.MODE_PRIVATE)

        val btn = view.findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            addService()
        }

        back.setOnClickListener {
            val fragment = ServiceFragment()
            showFragment(fragment)
        }



        return view
    }

    private fun addService() {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["name"] = name.text.toString()
        map["price"] = price.text.toString()

        apiInterface.addService(map).enqueue(object : Callback<AddServiceResponse> {

            override fun onResponse(call: Call<AddServiceResponse>, response: Response<AddServiceResponse>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                Toast.makeText(context, "Added succesfully !", Toast.LENGTH_SHORT).show()

                addServiceToKiosque(kiosque.body()!!._id)

            }

            override fun onFailure(call: Call<AddServiceResponse>, t: Throwable) {
                Toast.makeText(context, "Connexion 1 error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addServiceToKiosque(_id: String) {
        val apiInterface = ApiUser.create()
        val map: HashMap<String, String> = HashMap()
        map["produitId"] = _id

        apiInterface.addServiceToKiosque(map,mSharedPref.getString(USER_KIOSQUE,"hello")).enqueue(object : Callback<AddServiceToKiosque> {

            override fun onResponse(call: Call<AddServiceToKiosque>, response: Response<AddServiceToKiosque>) {

                val kiosque = response
                Log.e("response",kiosque.toString())
                Toast.makeText(context, "added to kiosque", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("id",_id )
                val fragment = AddImageToProductFragment()
                fragment.setArguments(bundle);
                showFragment(fragment)

            }
            override fun onFailure(call: Call<AddServiceToKiosque>, t: Throwable) {
                Toast.makeText(context, "Connexion 2 error!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun showFragment(fragment: Fragment){
        val fram = getParentFragmentManager().beginTransaction()
        fram.replace(R.id.fragmentContainerView4,fragment)
        fram.commit()
    }


}